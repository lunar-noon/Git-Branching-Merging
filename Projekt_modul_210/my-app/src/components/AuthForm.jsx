import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import { createClient } from '@supabase/supabase-js';

const supabase = createClient(
  '', 
  ''
);

const AuthForm = ({ isRegistering }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (isRegistering) {
        const { user, error: signUpError } = await supabase.auth.signUp({ email, password });
        if (signUpError) throw signUpError;

        const { error: insertError } = await supabase.from('users').insert([
          {
            id: user.id,         
            email: user.email,   
            created_at: new Date() 
          }
        ]);
        
        if (insertError) throw insertError;
        
        navigate('/login');
      } else {
        const { error: signInError } = await supabase.auth.signInWithPassword({ email, password });
        if (signInError) throw signInError;
        navigate('/');
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <h2>{isRegistering ? 'Registrierung' : 'Anmeldung'}</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="E-Mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Passwort"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">{isRegistering ? 'Registrieren' : 'Anmelden'}</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

AuthForm.propTypes = {
  isRegistering: PropTypes.bool.isRequired,
};

export default AuthForm;

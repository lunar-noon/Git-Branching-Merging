import React from 'react';
import { useNavigate } from 'react-router-dom';
import { createClient } from '@supabase/supabase-js';

const supabase = createClient(
  '',
  ''
);

const DeleteArticle = ({ articleId }) => {
  const navigate = useNavigate();

  const handleDelete = async () => {
    const { error } = await supabase
      .from('articles')
      .delete()
      .eq('id', articleId);

    if (error) {
      console.error("Fehler beim Löschen des Artikels:", error.message);
    } else {
      navigate('/');
    }
  };

  return (
    <div>
      <button onClick={handleDelete}>Artikel löschen</button>
    </div>
  );
};

export default DeleteArticle;

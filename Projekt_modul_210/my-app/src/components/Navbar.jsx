import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { createClient } from "@supabase/supabase-js";

const supabase = createClient(
  "",
  ""
);

const Navbar = () => {
  const [session, setSession] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {

    supabase.auth.getSession().then(({ data: { session } }) => {
      setSession(session);
    });

    const { subscription } = supabase.auth.onAuthStateChange(
      (_event, session) => {
        setSession(session);
      }
    );

    return () => {
      subscription?.unsubscribe && subscription.unsubscribe();
    };
  }, []);

  const handleLogout = async () => {
    await supabase.auth.signOut();
    setSession(null);
    navigate("/login");
  };

  return (
    <nav style={{ display: "flex", justifyContent: "space-between", padding: "5rem", background: "rgba(91, 97, 222, 0.49)", margin: "5rem", borderRadius: "5rem" }}>
      <div>
        <div>
          <Link to="/">Home</Link>
          {session && <Link to="/posts" style={{ marginLeft: "1rem" }}>Artikel ansehen</Link>}
        </div>
        <div>
          {session ? (
            <div>
              <span>Angemeldet als: {session.user.email}</span>
              <button onClick={handleLogout} style={{ marginLeft: "1rem" }}>
                Logout
              </button>
            </div>
          ) : (
            <Link to="/login">Login</Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
import React, { useState, useEffect } from "react";
import { createClient } from "@supabase/supabase-js";
import { useNavigate } from "react-router-dom";

const supabase = createClient(
  '', 
  '' 
);

const CreateArticle = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      const { data: session } = await supabase.auth.getSession();
      if (session) {
        setUser(session.user); 
      } else {
        navigate("/login"); 
      }
    };
    
    fetchUser();

    const { data: authListener } = supabase.auth.onAuthStateChange((event, session) => {
      if (session) {
        setUser(session.user);
      } else {
        setUser(null); 
        navigate("/login"); 
      }
    });

    
  }, [navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user) {
      alert("Du musst eingeloggt sein, um einen Artikel zu erstellen.");
      return;
    }

    
    const { error } = await supabase
      .from("articles")
      .insert([
        {
          title,
          content,
          user_id: user.id, 
        },
      ]);

    if (error) {
      console.error("Fehler beim Erstellen des Artikels:", error.message);
    } else {
      navigate("/"); 
    }
  };

  return (
    <div>
      <h2>Neuen Artikel erstellen</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Titel"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <textarea
          placeholder="Inhalt"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />
        <button type="submit">Artikel erstellen</button>
      </form>
    </div>
  );
};

export default CreateArticle;

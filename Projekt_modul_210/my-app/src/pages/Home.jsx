import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { createClient } from "@supabase/supabase-js";

const supabase = createClient(
  "",
  ""
);

const Home = () => {
  const [articles, setArticles] = useState([]);
  const [session, setSession] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    supabase.auth.getSession().then(({ data: { session } }) => {
      setSession(session);
    });

    const fetchArticles = async () => {
      const { data, error } = await supabase.from("articles").select();
      if (error) console.error(error);
      else setArticles(data);
    };

    fetchArticles();
  }, []);

  const handleDelete = async (id) => {
    const { error } = await supabase
      .from("articles")
      .delete()
      .eq("id", id);

    if (error) {
      console.error("Fehler beim Löschen des Artikels:", error.message);
    } else {
      setArticles(articles.filter(article => article.id !== id)); 
    }
  };

  if (!session) {
    return (
      <div style={{margin: "5rem", padding: "0rem 5rem 0rem 5rem"}}>
        <h1>Willkommen auf der Artikelübersicht</h1>
        <p>Bitte logge dich ein oder registriere dich, um fortzufahren.</p>
        <div>
          <Link to="/register">Registrieren</Link>
        </div>
      </div>
    );
  }

  return (
    <div style={{margin: "5rem", padding: "0rem 5rem 0rem 5rem"}}>
      <h1>Artikel Übersicht</h1>
      <div>
        <Link to="/create">Neuen Artikel erstellen</Link>
      </div>
      <div>
        {articles.length > 0 ? (
          articles.map((article) => (
            <div key={article.id}>
              <h3>{article.title}</h3>
              <p>{article.content}</p>
              <div>
              <Link to={`/edit-article/${article.id}`}>
                <button>Bearbeiten</button>
              </Link>
                <button onClick={() => handleDelete(article.id)}>Artikel löschen</button>
              </div>
            </div>
          ))
        ) : (
          <p>Keine Artikel gefunden</p>
        )}
      </div>
    </div>
  );
};

export default Home;

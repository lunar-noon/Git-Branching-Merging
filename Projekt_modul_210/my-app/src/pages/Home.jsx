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


  if (!session) {
    return (
      <div>
        <h1>Willkommen auf der Artikelübersicht</h1>
        <p>Bitte logge dich ein oder registriere dich, um fortzufahren.</p>
        <div>
           <Link to="/register">Registrieren</Link>
        </div>
      </div>
    );
  }

  return (
    <div>
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

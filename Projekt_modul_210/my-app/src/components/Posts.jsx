import React, { useEffect, useState } from "react";
import { createClient } from "@supabase/supabase-js";

const supabase = createClient(
  "",
  ""
);

const Posts = () => {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    const fetchArticles = async () => {
      const { data, error } = await supabase.from("articles").select("*");
      if (error) {
        console.error("Fehler beim Abrufen der Artikel:", error);
      } else {
        setArticles(data);
      }
    };

    fetchArticles();
  }, []);

  return (
    <div style={{margin: "5rem", padding: "0rem 5rem 0rem 5rem"}}>
      <h2>Alle Artikel</h2>
      {articles.length > 0 ? (
        <ul>
          {articles.map((article) => (
            <li key={article.id}>
              <h3>{article.title}</h3>
              <p>{article.content}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>Es gibt noch keine Artikel.</p>
      )}
    </div>
  );
};

export default Posts;

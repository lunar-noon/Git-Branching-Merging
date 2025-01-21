import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { createClient } from "@supabase/supabase-js";

const supabase = createClient(
  "",
  ""
);

const EditArticle = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [article, setArticle] = useState({ title: "", content: "" });

  useEffect(() => {
    const fetchArticle = async () => {
      const { data, error } = await supabase.from("articles").select().eq("id", id).single();
      if (error) {
        console.error("Fehler beim Abrufen des Artikels:", error.message);
      } else {
        setArticle(data);
      }
    };

    fetchArticle();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { error } = await supabase
      .from("articles")
      .update({
        title: article.title,
        content: article.content,
      })
      .eq("id", id);

    if (error) {
      console.error("Fehler beim Aktualisieren des Artikels:", error.message);
    } else {
      navigate(`/article/${id}`);
    }
  };

  return (
    <div>
      <h2>Artikel bearbeiten</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Titel"
          value={article.title}
          onChange={(e) => setArticle({ ...article, title: e.target.value })}
        />
        <textarea
          placeholder="Inhalt"
          value={article.content}
          onChange={(e) => setArticle({ ...article, content: e.target.value })}
        />
        <button type="submit">Artikel bearbeiten</button>
      </form>
    </div>
  );
};

export default EditArticle;

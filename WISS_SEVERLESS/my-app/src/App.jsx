import { useEffect, useState } from "react";
import { createClient } from "@supabase/supabase-js";
import { Auth } from "@supabase/auth-ui-react";
import { ThemeSupa } from "@supabase/auth-ui-shared";
import "./App.css";

// Supabase-Client initialisieren
const supabase = createClient(
  "---",
  "---"
);

function App() {
  const [notes, setNotes] = useState([]);
  const [themes, setThemes] = useState([]);
  const [session, setSession] = useState(null);
  const [newNote, setNewNote] = useState({ notiz: "", thema: "" });
  const [editingNote, setEditingNote] = useState(null); // Hinzugefügt für das Bearbeiten

  useEffect(() => {
    // Aktuelle Sitzung abrufen
    supabase.auth.getSession().then(({ data: { session } }) => {
      setSession(session);
      if (session) {
        getNotes();
        getThemes(); 
      }
    });

    // Authentifizierungsänderungen überwachen
    const { subscription } = supabase.auth.onAuthStateChange(
      (_event, session) => {
        setSession(session);
        if (session) {
          getNotes();
          getThemes();
        } else {
          setNotes([]);
          setThemes([]);
        }
      }
    );

    // Cleanup beim Verlassen der Komponente
    return () => {
      subscription?.unsubscribe && subscription.unsubscribe();
    };
  }, []);

  async function getNotes() {
    const { data, error } = await supabase.from("notizen").select();
    if (error) {
      console.error("Fehler beim Abrufen der Notizen:", error.message);
    } else {
      setNotes(data);
    }
  }

  async function getThemes() {
    const { data, error } = await supabase.from("thema").select();
    if (error) {
      console.error("Fehler beim Abrufen der Themen:", error.message);
    } else {
      setThemes(data);
    }
  }

  async function addNote() {
    if (!newNote.notiz || newNote.notiz.trim() === "") {
      console.error("Die Notiz darf nicht leer sein!");
      return;
    }

    const themaValue = newNote.thema && newNote.thema !== "" ? newNote.thema : null;
    const ownerValue = session.user.id;

    const { data, error } = await supabase
      .from("notizen")
      .insert([
        {
          notiz: newNote.notiz,
          thema: themaValue, // Hier wird `NULL` gesetzt, wenn kein Thema ausgewählt wurde
          owner: ownerValue, // Verwende hier die UUID des Benutzers
        },
      ]);

    if (error) {
      console.error("Fehler beim Hinzufügen der Notiz:", error.message);
    } else {
      setNotes([...notes, ...data]);
      setNewNote({ notiz: "", thema: "" });
    }
  }

  async function deleteNote(noteId) {
    const { error } = await supabase.from("notizen").delete().eq("id", noteId);
    if (error) {
      console.error("Fehler beim Löschen der Notiz:", error.message);
    } else {
      setNotes(notes.filter((note) => note.id !== noteId));
    }
  }

  async function updateNote() {
    if (!editingNote || !editingNote.notiz.trim()) {
      console.error("Die Notiz darf nicht leer sein!");
      return;
    }

    const { error } = await supabase
      .from("notizen")
      .update({ notiz: editingNote.notiz, thema: editingNote.thema })
      .eq("id", editingNote.id);

    if (error) {
      console.error("Fehler beim Aktualisieren der Notiz:", error.message);
    } else {
      setNotes(
        notes.map((note) =>
          note.id === editingNote.id ? { ...note, ...editingNote } : note
        )
      );
      setEditingNote(null);
    }
  }

  // Benutzer ausloggen
  async function handleLogout() {
    const { error } = await supabase.auth.signOut();
    if (error) {
      console.error("Fehler beim Abmelden:", error.message);
    } else {
      setNotes([]); // Notizen leeren, wenn der Benutzer sich abmeldet
    }
  }

  // Benutzer nicht angemeldet -> Authentifizierungsansicht anzeigen
  if (!session) {
    return (
      <div className="container" style={{ maxWidth: "400px", margin: "50px auto" }}>
        <Auth
          supabaseClient={supabase}
          appearance={{ theme: ThemeSupa }}
          providers={["google", "github"]}
        />
      </div>
    );
  }

  return (
    <div className="container">
      <h1>Notizen</h1>
      <button onClick={handleLogout}>Logout</button>
      <ul>
        {notes.length > 0 ? (
          notes.map((note) => (
            <li key={note.id}>
              <strong>{note.thema}</strong>: {note.notiz}
              <div className="data-buttons">
                <button onClick={() => deleteNote(note.id)}>Löschen</button>
                <button onClick={() => setEditingNote(note)}>Bearbeiten</button>  
              </div>
            </li>
          ))
        ) : (
          <p>Keine Notizen verfügbar</p>
        )}
      </ul>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          addNote();
        }}
      >
        <textarea
          placeholder="Neue Notiz..."
          value={newNote.notiz}
          onChange={(e) => setNewNote({ ...newNote, notiz: e.target.value })}
          required
        />
        <select
          value={newNote.thema}
          onChange={(e) => setNewNote({ ...newNote, thema: e.target.value })}
        >
          <option value="">Thema auswählen</option>
          {themes.map((theme) => (
            <option key={theme.id} value={theme.id}>
              {theme.thema}
            </option>
          ))}
        </select>
        <input type="submit" value="Notiz hinzufügen" />
      </form>

      {editingNote && (
        <div>
          <h3>Notiz bearbeiten</h3>
          <form
            onSubmit={(e) => {
              e.preventDefault();
              updateNote();
            }}
          >
            <textarea
              value={editingNote.notiz}
              onChange={(e) =>
                setEditingNote({ ...editingNote, notiz: e.target.value })
              }
            />
            <select
              value={editingNote.thema}
              onChange={(e) =>
                setEditingNote({ ...editingNote, thema: e.target.value })
              }
            >
              <option value="">Thema auswählen</option>
              {themes.map((theme) => (
                <option key={theme.id} value={theme.id}>
                  {theme.thema}
                </option>
              ))}
            </select>
            <button type="submit">Speichern</button>
            <button type="button" onClick={() => setEditingNote(null)}>
              Abbrechen
            </button>
          </form>
        </div>
      )}
    </div>
  );
}

export default App;
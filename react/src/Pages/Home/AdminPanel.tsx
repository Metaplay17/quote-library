import React, { useEffect, useState } from 'react';
import './css/AdminPanel.css';
import { useNotificationDialog } from '../../Modals/NotificationContext';
import { useNavigate } from 'react-router-dom';
import { makeSafeGet, makeSafePost } from '../../util';
import type { Author, AuthorsListResponse } from '../../types';

const AdminPanel: React.FC = () => {

  const navigate = useNavigate();
  const { showAlert, showConfirm } = useNotificationDialog();

  const [authorName, setAuthorName] = useState<string>('');
  const [tagName, setTagName] = useState<string>('');

    const [allAuthors, setAllAuthors] = useState<Author[]>([]);
    const [filteredAuthors, setFilteredAuthors] = useState<Author[]>([]);
    const [searchAuthorTerm, setSearchAuthorTerm] = useState<string>('');

  const [quoteText, setQuoteText] = useState<string>('');
  const [quoteContext, setQuoteContext] = useState<string>('');
  const [quoteAuthor, setQuoteAuthor] = useState<string>('');
  const [newTag, setNewTag] = useState<string>('');
  const [tags, setTags] = useState<string[]>([]);

  useEffect(() => {
      setFilteredAuthors(allAuthors.filter(a => a.name.toLowerCase().includes(searchAuthorTerm.toLowerCase())));
  }, [searchAuthorTerm]);

  const fetchAuthors = async () => {
      const response : Response | null = await makeSafeGet("/authors", navigate, showAlert);
      if (response === null) {
        return;
      }
      const json : AuthorsListResponse = await response.json();
      setAllAuthors(json.authors);
      setFilteredAuthors(json.authors);
  }

  useEffect(() => {
    fetchAuthors();
  }, [])

  const handleAddAuthor = async (e: React.FormEvent) => {
    e.preventDefault();
    if (authorName.trim()) {
      const response = await makeSafePost("/authors", {
        name: authorName
      }, navigate, showAlert);
      if (response === null) {
        return;
      }
      showAlert({
        title: "Успех",
        message: "Автор добавлен"
      });
      setAuthorName('');
    }
  };

  const handleAddTagGlobal = async (e: React.FormEvent) => {
    e.preventDefault();
    if (tagName.trim()) {
      showAlert({
        title: "Успех",
        message: "Тег добавлен"
      });
      setTagName('');
    }
    await makeSafePost("/tags", {
      name: tagName
    }, navigate, showAlert);
  };

  const handleAddTagToQuote = () => {
    if (newTag.trim() && !tags.includes(newTag.trim())) {
      setTags([...tags, newTag.trim()]);
      setNewTag('');
    }
  };

  const handleRemoveTag = (tag : string) => {
    setTags(tags.filter((t) => t != tag));
  }

  const handleSubmitQuote = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!quoteText.trim() || !quoteAuthor.trim()) {
      showAlert({
          title: "Ошибка",
          message: "Заполните название и автора цитаты"
      });
      return;
    }
    const response : Response | null = await makeSafePost("/quotes", {
      text: quoteText,
      authorId: filteredAuthors.length == 1 ? filteredAuthors[0].id : null,
      context: quoteContext,
      tags: tags
    }, navigate, showAlert);
    if (response === null) {
      return;
    }
    showAlert({
        title: "Успех",
        message: `Цитата добавлена:\n"${quoteText}"\n— ${quoteAuthor}\nКонтекст: ${quoteContext || '—'}\nТеги: ${tags.join(', ')}`
    });
    setQuoteText('');
    setQuoteContext('');
    setQuoteAuthor('');
    setTags([]);
  };

  return (
    <div className="admin-panel">
      <div className="admin-layout">
        {/* Левый блок */}
        <aside className="admin-sidebar">
          {/* Форма: добавить автора */}
          <div className="admin-form-card">
            <h3>Добавить автора</h3>
            <form onSubmit={handleAddAuthor}>
              <input
                type="text"
                placeholder="Имя автора"
                value={authorName}
                onChange={(e) => setAuthorName(e.target.value)}
                required
              />
              <button type="submit" className="submit-button">Отправить</button>
            </form>
          </div>

          {/* Форма: добавить тег */}
          <div className="admin-form-card">
            <h3>Добавить тег</h3>
            <form onSubmit={handleAddTagGlobal}>
              <input
                type="text"
                placeholder="Название тега"
                value={tagName}
                onChange={(e) => setTagName(e.target.value)}
                required
              />
              <button type="submit" className="submit-button">Отправить</button>
            </form>
          </div>
        </aside>

        {/* Правый блок */}
        <main className="quote-editor">
          <h2>Добавить новую цитату</h2>
          <form onSubmit={handleSubmitQuote} className="quote-form">
            <div className="form-row">
              <label>Текст цитаты</label>
              <textarea
                value={quoteText}
                onChange={(e) => setQuoteText(e.target.value)}
                placeholder="Введите текст цитаты..."
                rows={4}
                required
              />
            </div>

            <div className="form-row">
              <label>Контекст (опционально)</label>
              <textarea
                value={quoteContext}
                onChange={(e) => setQuoteContext(e.target.value)}
                placeholder="Где и когда была сказана цитата..."
                rows={2}
              />
            </div>

            <div className="form-row">
              <label>Автор</label>
              <input
                type="text"
                value={quoteAuthor}
                onChange={(e) => {setQuoteAuthor(e.target.value); setSearchAuthorTerm(e.target.value)}}
                placeholder="Имя автора"
                list="authors-datalist"
                required
              />
              <datalist id="authors-datalist">
                {filteredAuthors.map(a => (
                  <option>
                    {a.name}
                  </option>
                ))}
              </datalist>
            </div>

            {/* Мини-форма для тегов к цитате */}
            <div className="form-row">
              <label>Теги</label>
              <div className="tags-input-group">
                <input
                  type="text"
                  value={newTag}
                  onChange={(e) => setNewTag(e.target.value)}
                  placeholder="Название тега"
                />
                <button
                  type="button"
                  onClick={handleAddTagToQuote}
                  className="add-tag-button"
                >
                  Добавить
                </button>
              </div>
              <div className="tags-preview">
                {tags.map((tag, index) => (
                  <span key={index} className="tag-badge">
                    {tag} <span onClick={() => handleRemoveTag(tag)} className="delete-tag-button">❌</span>
                  </span>
                ))}
              </div>
            </div>

            <button type="submit" className="submit-quote-button">
              Добавить цитату
            </button>
          </form>
        </main>
      </div>
    </div>
  );
};

export default AdminPanel;
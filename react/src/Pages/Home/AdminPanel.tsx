import React, { useEffect, useState } from 'react';
import styles from './css/AdminPanel.module.css';
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
    if (tags.length == 0) {
      showAlert({
          title: "Ошибка",
          message: "У цитаты должен быть минимум один тег"
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
    <div className={styles.admin_panel}>
      <div className={styles.admin_layout}>
        {/* Левый блок */}
        <aside className={styles.admin_sidebar}>
          {/* Форма: добавить автора */}
          <div className={styles.admin_form_card}>
            <h3>Добавить автора</h3>
            <form onSubmit={handleAddAuthor}>
              <input
                type="text"
                placeholder="Имя автора"
                value={authorName}
                onChange={(e) => setAuthorName(e.target.value)}
                required
              />
              <button type="submit" className={styles.submit_button}>Отправить</button>
            </form>
          </div>

          {/* Форма: добавить тег */}
          <div className={styles.admin_form_card}>
            <h3>Добавить тег</h3>
            <form onSubmit={handleAddTagGlobal}>
              <input
                type="text"
                placeholder="Название тега"
                value={tagName}
                onChange={(e) => setTagName(e.target.value)}
                required
              />
              <button type="submit" className={styles.submit_button}>Отправить</button>
            </form>
          </div>
        </aside>

        {/* Правый блок */}
        <main className={styles.quote_editor}>
          <h2>Добавить новую цитату</h2>
          <form onSubmit={handleSubmitQuote} className={styles.quote_form}>
            <div className={styles.form_row}>
              <label>Текст цитаты</label>
              <textarea
                value={quoteText}
                onChange={(e) => setQuoteText(e.target.value)}
                placeholder="Введите текст цитаты..."
                rows={1}
                required
              />
            </div>

            <div className={styles.form_row}>
              <label>Контекст (опционально)</label>
              <textarea
                value={quoteContext}
                onChange={(e) => setQuoteContext(e.target.value)}
                placeholder="Где и когда была сказана цитата..."
                rows={1}
              />
            </div>

            <div className={styles.form_row}>
              <label>Автор</label>
              <input
                type="text"
                value={quoteAuthor}
                onChange={(e) => {
                  setQuoteAuthor(e.target.value);
                  setSearchAuthorTerm(e.target.value);
                }}
                placeholder="Имя автора"
                list="authors-datalist"
                required
              />
              <datalist id="authors-datalist">
                {filteredAuthors.map(a => (
                  <option key={a.id}>{a.name}</option>
                ))}
              </datalist>
            </div>

            {/* Мини-форма для тегов к цитате */}
            <div className={styles.form_row}>
              <label>Теги</label>
              <div className={styles.tags_input_group}>
                <input
                  type="text"
                  value={newTag}
                  onChange={(e) => setNewTag(e.target.value)}
                  placeholder="Название тега"
                />
                <button
                  type="button"
                  onClick={handleAddTagToQuote}
                  className={styles.add_tag_button}
                >
                  Добавить
                </button>
              </div>
              <div className={styles.tags_preview}>
                {tags.map((tag, index) => (
                  <span key={index} className={styles.tag_badge}>
                    {tag} <span onClick={() => handleRemoveTag(tag)} className={styles.delete_tag_button}>❌</span>
                  </span>
                ))}
              </div>
            </div>

            <button type="submit" className={styles.submit_quote_button}>
              Добавить цитату
            </button>
          </form>
        </main>
      </div>
    </div>
  );
};

export default AdminPanel;
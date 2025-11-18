import React, { useState } from 'react';
import './css/QuotesBlock.css';

// Тип для цитаты
interface Quote {
  id: number;
  text: string;
  author: string;
  tags: string[];
  context: string
}

const QuotesBlock: React.FC = () => {
  // Пример данных
  const initialQuotes: Quote[] = [
    { id: 1, text: 'Цитата 1', author: 'Автор 1', tags: ['вдохновение', 'жизнь'], context: "XD" },
    { id: 2, text: 'Цитата 2', author: 'Автор 2', tags: ['мудрость', 'время'], context: "XD" },
    { id: 3, text: 'Цитата 3', author: 'Автор 3', tags: ['настроение'], context: "XD" },
    { id: 4, text: 'Цитата 4', author: 'Автор 4', tags: ['настроение', 'вдохновение'], context: "XD" },
    { id: 5, text: 'Цитата 5', author: 'Автор 5', tags: ['мудрость', 'жизнь'], context: "XD" },
  ];

  const [quotes, setQuotes] = useState<Quote[]>(initialQuotes);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  const [isNextQuotes, setIsNextQuotes] = useState<boolean>(true);
  const [isPrevQuotes, setIsPrevQuotes] = useState<boolean>(false);

  const allTags = Array.from(new Set(initialQuotes.flatMap(q => q.tags))); // С сервера

  const handleTagChange = (tag: string) => {
    if (selectedTags.includes(tag)) {
      setSelectedTags(selectedTags.filter(t => t !== tag));
    } else {
      setSelectedTags([...selectedTags, tag]);
    }
  };

  const handleRandomQuotes = () => {
    alert('Загружены случайные цитаты!');
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    alert(`Поиск по: ${searchTerm}, теги: ${selectedTags.join(', ')}`);
  };

  return (
    <div className="quote-block">
      {/* Поиск */}
      <div className="search-section">
        <form onSubmit={handleSearch} className="search-form">
          <input
            type="text"
            placeholder="Поиск цитат..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          <button type="submit" className="search-button">Найти</button>
          <button type="button" onClick={handleRandomQuotes} className="random-button">Случайные цитаты</button>
        </form>
      </div>

      <div className="content-layout">
        {/* Блок с тегами */}
        <aside className="tags-sidebar">
          <h3>Фильтры по тегам</h3>
          <div className="tags-list">
            {allTags.map(tag => (
              <label key={tag} className="tag-checkbox">
                <input
                  type="checkbox"
                  checked={selectedTags.includes(tag)}
                  onChange={() => handleTagChange(tag)}
                />
                {tag}
              </label>
            ))}
          </div>
        <button className="search-button">Обновить</button>
        </aside>

        {/* Блок с цитатами */}
        <section className="quotes-container">
          <div className="quotes-grid">
            {quotes.map(quote => (
              <div key={quote.id} className="quote-card">
                <p className="quote-text">"{quote.text}"</p>
                <p className="quote-author">— {quote.author}</p>
                <p className="quote-context">Контекст: {quote.context}</p>
                <div className="quote-tags">
                  {quote.tags.map(tag => (
                    <span key={tag} className="tag-badge">{tag}</span>
                  ))}
                </div>
                <button className="favorite-button">❤️ Добавить в избранное</button>
              </div>
            ))}
          </div>

          {/* Пагинация */}
          <div className="pagination">
            <button
                onClick={() => null}
                disabled={!isPrevQuotes}
                className="pagination-button"
            >
                {'<'}
            </button>
            <button
                    onClick={() => null}
                    disabled={!isNextQuotes}
                    className="pagination-button"
            >
                {'>'}
            </button>
          </div>
        </section>
      </div>
    </div>
  );
};

export default QuotesBlock;
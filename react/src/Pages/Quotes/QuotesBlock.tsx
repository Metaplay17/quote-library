import React, { useEffect, useState } from 'react';
import './css/QuotesBlock.css';
import { type Tag, type Quote, type QuotesListResponse, type TagsListResponse } from '../../types';
import { makeSafeGet } from '../../util';
import { useNavigate } from 'react-router-dom';
import { useNotificationDialog } from '../../Modals/NotificationContext';


const QuotesBlock: React.FC = () => {
    const navigate = useNavigate();
    const { showAlert, showConfirm } = useNotificationDialog();

    const [quotes, setQuotes] = useState<Quote[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [allTags, setAllTags] = useState<Tag[]>([]);
    const [selectedTags, setSelectedTags] = useState<Tag[]>([]);
    const [isNextQuotes, setIsNextQuotes] = useState<boolean>(true);
    const [isPrevQuotes, setIsPrevQuotes] = useState<boolean>(false);
    const [startIndex, setStartIndex] = useState<number>(0);

    const fetchRandomQuotes = async () => {
        try {
            const response : Response = await makeSafeGet("/quotes/random", navigate, showAlert);
            const json : QuotesListResponse = await response.json();
            setQuotes(json.quotes);
        } catch (ex : any) {}
    }

    useEffect(() => {
        const fetchTags = async () => {
            const response : Response = await makeSafeGet("/tags", navigate, showAlert);
            const json : TagsListResponse = await response.json();
            setAllTags(json.tags);
        }

        fetchTags();
        fetchRandomQuotes();
    }, []);

    const handleTagChange = (tag: Tag) => {
        if (selectedTags.includes(tag)) {
            setSelectedTags(selectedTags.filter(t => t !== tag));
        } else {
            setSelectedTags([...selectedTags, tag]);
        }
    };

    const handleRandomQuotes = async () => {
        await fetchRandomQuotes();
    };

    const handleSearch = async (e: React.FormEvent) => {
        e.preventDefault();
        const response = await makeSafeGet(`/quotes/search?pattern=${searchTerm}&startIndex=${startIndex}`, navigate, showAlert);
        const json : QuotesListResponse = await response.json();
        if (json.quotes.length < 5) {
            setIsNextQuotes(false);
        }
        else {
          setIsNextQuotes(true);
        }
        setQuotes(json.quotes);
    };

    const handleNextButton = async (e : React.FormEvent) => {
        if (!isNextQuotes) {
            return;
        }
        setStartIndex(prev => prev + 5);
        await handleSearch(e);
    }

    const handlePrevButton = async (e : React.FormEvent) => {
        if (!isPrevQuotes) {
            return;
        }
        setStartIndex(prev => prev - 5);
        if (startIndex <= 0) {
            setIsPrevQuotes(false);
        }
        await handleSearch(e);
    }

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
                  <label key={tag.id} className="tag-checkbox">
                    <input
                      type="checkbox"
                      checked={selectedTags.includes(tag)}
                      onChange={() => handleTagChange(tag)}
                    />
                    {tag.name}
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
                    <p className="quote-context">Контекст: {quote.context === null ? "Отсутствует" : quote.context}</p>
                    <div className="quote-tags">
                      {quote.tags.map(tag => (
                        <span key={tag.id} className="tag-badge">{tag.name}</span>
                      ))}
                    </div>
                    <button className="favorite-button">❤️ Добавить в избранное</button>
                  </div>
                ))}
                {quotes.length == 0 ? <h3>Цитат нет</h3> : ""}
              </div>

              {/* Пагинация */}
              <div className="pagination">
                <button
                    onClick={(e) => handleNextButton(e)}
                    disabled={!isPrevQuotes}
                    className="pagination-button"
                >
                    {'<'}
                </button>
                <button
                        onClick={(e) => handlePrevButton(e)}
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
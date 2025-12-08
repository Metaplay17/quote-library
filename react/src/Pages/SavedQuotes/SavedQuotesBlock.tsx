import React, { useEffect, useState } from 'react';
import styles from '../Quotes/css/QuotesBlock.module.css';
import { type Tag, type Quote, type QuotesListResponse, type TagsListResponse, type Author, type AuthorsListResponse } from '../../types';
import { makeSafeGet, makeSafePost } from '../../util';
import { useNavigate } from 'react-router-dom';
import { useNotificationDialog } from '../../Modals/NotificationContext';


const SavedQuotesBlock: React.FC = () => {
    const navigate = useNavigate();
    const { showAlert, showConfirm } = useNotificationDialog();

    const [quotes, setQuotes] = useState<Quote[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');

    const [allTags, setAllTags] = useState<Tag[]>([]);
    const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

    const [allAuthors, setAllAuthors] = useState<Author[]>([]);
    const [filteredAuthors, setFilteredAuthors] = useState<Author[]>([]);
    const [searchAuthorTerm, setSearchAuthorTerm] = useState<string>('');
    const [selectedAuthors, setSelectedAuthors] = useState<Author[]>([]);

    const [isNextQuotes, setIsNextQuotes] = useState<boolean>(true);
    const [isPrevQuotes, setIsPrevQuotes] = useState<boolean>(false);
    const [startIndex, setStartIndex] = useState<number>(0);

    useEffect(() => {
        setFilteredAuthors(allAuthors.filter(a => a.name.toLowerCase().includes(searchAuthorTerm.toLowerCase())));
    }, [searchAuthorTerm]);

    const fetchSavedQuotes = async () => {
        try {
            const response : Response | null = await makeSafeGet(`/user/saved-quotes?startIndex=${startIndex}`, navigate, showAlert);
            if (response === null) {
              return;
            }
            const json : QuotesListResponse = await response.json();
            setQuotes(json.quotes);
            if (json.quotes.length < 4) {
              setIsNextQuotes(false);
            }
        } catch (ex : any) {}
    }

    useEffect(() => {
        const fetchTags = async () => {
            const response : Response | null = await makeSafeGet("/tags", navigate, showAlert);
            if (response === null) {
              return;
            }
            const json : TagsListResponse = await response.json();
            setAllTags(json.tags);
        }

        const fetchAuthors = async () => {
            const response : Response | null = await makeSafeGet("/authors", navigate, showAlert);
            if (response === null) {
              return;
            }
            const json : AuthorsListResponse = await response.json();
            setAllAuthors(json.authors);
            setFilteredAuthors(json.authors);
        }

        fetchTags();
        fetchAuthors();
        fetchSavedQuotes();
    }, []);

    const handleTagChange = (tag: Tag) => {
        if (selectedTags.includes(tag)) {
            setSelectedTags(selectedTags.filter(t => t !== tag));
        } else {
            setSelectedTags([...selectedTags, tag]);
        }
    };

    const handleSearch = async (e: React.FormEvent, startIndex: number) => {
        e.preventDefault();
        const response : Response | null = await makeSafePost("/user/saved-quotes/find", {
            pattern: searchTerm,
            tagsId: selectedTags.map(t => t.id),
            authorsId: selectedAuthors.map(a => a.id),
            startIndex: startIndex
        }, navigate, showAlert);
        if (response === null) {
          return;
        }
        const json : QuotesListResponse = await response.json();
        if (json.quotes.length < 4) {
            setIsNextQuotes(false);
        }
        else {
          setIsNextQuotes(true);
        }
        if (startIndex != 0) {
          setIsPrevQuotes(true);
        }
        else {
          setIsPrevQuotes(false);
        }
        setQuotes(json.quotes);
    };

    const handleNextButton = async (e : React.FormEvent) => {
        if (!isNextQuotes) {
            return;
        }
        setStartIndex(prev => prev + 4);
        await handleSearch(e, startIndex + 4);
    }

    const handlePrevButton = async (e : React.FormEvent) => {
        if (!isPrevQuotes) {
            return;
        }
        setStartIndex(prev => prev - 4);
        if (startIndex <= 0) {
            setIsPrevQuotes(false);
        }
        await handleSearch(e, startIndex - 4);
    }

    const handleAuthorAdd = () => {
      if (filteredAuthors.length != 1) {
        showAlert({
          title: "Ошибка добавления",
          message: "Проверьте корректность имени автора"
        });
        return;
      }
      if (selectedAuthors.includes(filteredAuthors[0])) {
        showAlert({
          title: "Ошибка добавления",
          message: "Автор уже добавлен"
        });
        return;
      }
      setSelectedAuthors([...selectedAuthors, filteredAuthors[0]]);
      setSearchAuthorTerm('');
    }

    const handleDeleteQuote = async (quoteId : number) => {
      const response : Response | null = await makeSafePost("/user/delete", {
        quoteId: quoteId
      }, navigate, showAlert);
      if (response === null) {
        return;
      }
      window.location.reload();
    }

  return (
    <div className={styles.quote_block}>
      {/* Поиск */}
      <div className={styles.search_section}>
        <form onSubmit={(e) => handleSearch(e, startIndex)} className={styles.search_form}>
          <input
            type="text"
            placeholder="Поиск цитат..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className={styles.search_input}
          />
          <button type="submit" className={styles.search_button}>Найти</button>
        </form>
      </div>

      <div className={styles.content_layout}>
        {/* Блок с тегами */}
        <aside className={styles.tags_sidebar}>
          <h3>Фильтры по тегам</h3>
          <div className={styles.tags_list}>
            {allTags.map(tag => (
              <label key={tag.id} className={styles.tag_checkbox}>
                <input
                  type="checkbox"
                  checked={selectedTags.includes(tag)}
                  onChange={() => handleTagChange(tag)}
                />
                {tag.name}
              </label>
            ))}
          </div>
        </aside>

        {/* Блок с цитатами */}
        <section className={styles.quotes_container}>
          <div className={styles.quotes_grid}>
            {quotes.map(quote => (
              <div key={quote.id} className={styles.quote_card}>
                <p className={styles.quote_text}>"{quote.text}"</p>
                <p className={styles.quote_author}>— {quote.author}</p>
                <p className={styles.quote_context}>
                  Контекст: {quote.context === null ? "Отсутствует" : quote.context}
                </p>
                <div className={styles.quote_description}>
                  <button className={styles.favorite_button} onClick={() => handleDeleteQuote(quote.id)}>
                    💔 Удалить из избранного
                  </button>
                  <div className={styles.quote_tags}>
                    {quote.tags.map(tag => (
                      <span key={tag.id} className={styles.tag_badge}>{tag.name}</span>
                    ))}
                  </div>
                </div>
              </div>
            ))}
            {quotes.length === 0 ? <h3 className={styles.no_quotes}>Цитат нет</h3> : null}
          </div>

          {/* Пагинация */}
          <div className={styles.pagination}>
            <button
              onClick={(e) => handlePrevButton(e)}
              disabled={!isPrevQuotes}
              className={styles.pagination_button}
            >
              {'<'}
            </button>
            <button
              onClick={(e) => handleNextButton(e)}
              disabled={!isNextQuotes}
              className={styles.pagination_button}
            >
              {'>'}
            </button>
          </div>
        </section>

        {/* Блок с авторами */}
        <aside className={styles.authors_sidebar}>
          <h3>Фильтры по авторам</h3>
          <div className={styles.search_authors_block}>
            <input
              id="search-authors-input"
              type="text"
              placeholder="Поиск автора..."
              value={searchAuthorTerm}
              onChange={(e) => setSearchAuthorTerm(e.target.value)}
              className={styles.search_author_input}
              list="authors-datalist"
            />
            <datalist id="authors-datalist">
              {filteredAuthors.map(a => (
                <option key={a.id}>{a.name}</option>
              ))}
            </datalist>
            <button onClick={() => handleAuthorAdd()} className={styles.search_button}>
              Добавить
            </button>
          </div>

          <div className={styles.authors_search}>
            {selectedAuthors.length === 0 ? (
              <p className={styles.no_selected_authors}>Тут будут выбранные авторы</p>
            ) : (
              selectedAuthors.map(author => (
                <div key={author.id} className={styles.author_search_block}>
                  <p>{author.name}</p>
                  <button
                    onClick={() => setSelectedAuthors(selectedAuthors.filter(a => a.id !== author.id))}
                    className={styles.delete_author_button}
                  >
                    Удалить
                  </button>
                </div>
              ))
            )}
          </div>
        </aside>
      </div>
    </div>
  );
};

export default SavedQuotesBlock;
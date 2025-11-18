import React from 'react';
import './css/MainPage.css';
import { useNavigate } from 'react-router-dom';

interface MainPageProps {
  children?: React.ReactNode; // Компонент, отображаемый в основном блоке
}

const MainPage: React.FC<MainPageProps> = ({ children }) => {
    const navigate = useNavigate();

    return (
        <div className="main-page">
        {/* Хедер */}
        <header className="main-header">
            <nav className="nav-menu">
            <ul>
                <li><a onClick={() => navigate('/home')}>Домашняя страница</a></li>
                <li><a onClick={() => navigate('/quotes')}>Обзор цитат</a></li>
                <li><a onClick={() => navigate('/saved-quotes')}>Мои цитаты</a></li>
                <li><a onClick={() => navigate('/settings')}>Настройки</a></li>
            </ul>
            </nav>
        </header>

        {/* Основной блок */}
        <main className="main-content">
            {children}
        </main>

        {/* Футер */}
        <footer className="main-footer">
            <p>Библиотека цитат</p>
        </footer>
        </div>
    );
};

export default MainPage;
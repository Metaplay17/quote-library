import React, { useState } from 'react';
import './css/LoginForm.css';
import { isValidPassword, isValidUsername } from '../../util';
import { useNavigate } from 'react-router-dom';
import { useNotificationDialog } from '../../Modals/NotificationContext';
import type { DefaultResponse } from '../../types';

const LoginForm: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const { showAlert, showConfirm } = useNotificationDialog();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        e.preventDefault();
        if (!isValidUsername(username)) {
            showAlert({
                title: "Ошибка",
                message: "Имя пользователя должно начинаться с буквы, быть длиной не менее 3 символов, а также не содержать специальных символов, кроме нижнего подчёркивания"
            });
            return;
        }
        if (!isValidPassword(password)) {
            showAlert({
                title: "Ошибка",
                message: "Пароль должен быть длиной не менее 8 символов, а также содержать минимум 1 цифру и минимум 1 заглавную букву"
            });
            return;
        }

        const response : Response = await fetch("/api/auth/register", {
            method: "POST",
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            const json : DefaultResponse = await response.json();
            showAlert({
                title: json.status,
                message: json.message
            });
            return;
        }
        navigate('/login');
  };

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2>Вход</h2>
        <div className="input-group">
          <label htmlFor="username">Имя пользователя</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Пароль</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="login-button">
          Войти
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
import React, { useState } from 'react';
import styles from './css/LoginForm.module.css';
import { isValidPassword, isValidUsername } from '../../util';
import { useNavigate } from 'react-router-dom';
import { useNotificationDialog } from '../../Modals/NotificationContext';
import type { DefaultResponse } from '../../types';
import type LoginResponse from '../../types';

const LoginForm: React.FC = () => {
    const API_URL : string = import.meta.env.VITE_API_URL;

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

        const response : Response = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
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
        const json : LoginResponse = await response.json();
        localStorage.setItem("token", json.token);
        localStorage.setItem("username", json.username);
        localStorage.setItem("privilegeLevel", json.privilegeLevel.toString());
        navigate('/home');
  };

  return (
    <div className={styles.login_container}>
      <form onSubmit={handleSubmit} className={styles.login_form}>
        <h2>Вход</h2>
        <div className={styles.input_group}>
          <label htmlFor="username">Имя пользователя</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className={styles.input_group}>
          <label htmlFor="password">Пароль</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className={styles.login_button}>
          Войти
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
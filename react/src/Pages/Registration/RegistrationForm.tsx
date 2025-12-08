import { useState, type FormEvent } from 'react';
import styles from './css/RegistrationForm.module.css';
import { useNotificationDialog } from '../../Modals/NotificationContext';
import { isValidPassword, isValidUsername } from '../../util';
import type { DefaultResponse } from '../../types';
import { useNavigate } from 'react-router-dom';

const RegistrationForm = () => {
    const API_URL : string = import.meta.env.VITE_API_URL;

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const { showAlert, showConfirm } = useNotificationDialog();
    const navigate = useNavigate();

    const handleSubmit = async (e : FormEvent) : Promise<void> => {
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

        const response : Response = await fetch(`${API_URL}/auth/register`, {
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
        navigate('/login');
    };

    return (
        <div className={styles.registration_container}>
        <form onSubmit={handleSubmit} className={styles.registration_form}>
            <h2>Регистрация</h2>
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
            <button type="submit" className={styles.register_button}>
            Зарегистрироваться
            </button>
        </form>
        </div>
    );
};

export default RegistrationForm;
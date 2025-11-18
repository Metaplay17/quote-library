import { useNavigate } from 'react-router-dom';
import './css/RegistrationPage.css'
import RegistrationForm from './RegistrationForm';

const RegistrationPage = () => {
    const navigate = useNavigate();

    return (
        <main>
            <h1>Библиотека Цитат</h1>
            <RegistrationForm />
            <a onClick={(e) => { e.preventDefault(); navigate('/login') } }>Уже зарегистрированы? Войти</a>
        </main>
    )
}

export default RegistrationPage;
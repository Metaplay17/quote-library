import { useNavigate } from "react-router-dom";
import LoginForm from "./LoginForm";
import './css/LoginPage.css'

const LoginPage = () => {
    const navigate = useNavigate();

    return (
        <div className="login-page-main">
            <h1>Библиотека Цитат</h1>
            <LoginForm />
            <a onClick={(e) => { e.preventDefault(); navigate('/registration') } }>Нет аккаунта? Зарегистрироваться</a>
        </div>
    )
}

export default LoginPage;
import { useEffect } from 'react';
import styles from './css/HomeBlock.module.css';
import { makeSafeGet } from '../../util';
import { useNavigate } from 'react-router-dom';
import { useNotificationDialog } from '../../Modals/NotificationContext';
import type { Profile, ProfileResponse } from '../../types';
import AdminPanel from './AdminPanel';

const HomeBlock = () => {
    const navigate = useNavigate();
    const { showAlert, showConfirm } = useNotificationDialog();

    useEffect(() => {
        const fetchProfile = async () => {
            const response : Response | null = await makeSafeGet("/user/profile", navigate, showAlert);
            if (response === null) {
                return;
            }
            const profile : ProfileResponse = await response.json();
            localStorage.setItem('username', profile.profile.username);
            localStorage.setItem('privilegeLevel', profile.profile.privilegeLevel.toString());
        }

        fetchProfile();
    }, []);

    if (Number(localStorage.getItem('privilegeLevel')) < 5) {
        return (
            <main className={styles.main_home_user}>
                <h1>Текущий пользователь:</h1>
                <h2>{localStorage.getItem("username")}</h2>
            </main>

        )
    }
    else {
        return (
            <main className={styles.main_home}>
                <AdminPanel />
            </main>

        )
    }

}

export default HomeBlock;
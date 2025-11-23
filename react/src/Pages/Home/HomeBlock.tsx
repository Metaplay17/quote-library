import './css/HomeBlock.css';

const HomeBlock = () => {
    return (
        <main>
            <h2>{localStorage.getItem("username")}</h2>
        </main>

    )
}

export default HomeBlock;
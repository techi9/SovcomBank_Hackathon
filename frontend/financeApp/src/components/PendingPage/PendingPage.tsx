import "./PendingPage.sass"
import "../../styles/App.sass"
import Attention from '../../assets/attention.svg';
import Footer from "../Footer/Footer";

function PendingPage(){
    return (
        <>
        <div className={"main-pane pane attention-page"}>
            <div>
            <img src={Attention}/>
            <p className={"extra-info"}>Ваш аккаунт в данный момент проходит модерацию. Как только мы активируем Вашу учётную запись, мы сразу Вам сообщим. С уваженим, команда Совкомбанка!</p>
            </div>

        </div>
            <Footer/>
        </>)

}

export default PendingPage
// import reactLogo from './assets/react.svg'
import './styles/App.sass'
import HeaderPublic from "./components/HeaderPublic/HeaderPublic";
import LogIn from "./components/Autorization/LogIn/LogIn";
import Registration from "./components/Autorization/Registration/Registration";
import HeaderPrivate from "./components/HeaderPrivate/HeaderPrivate";
import PendingPage from "./components/PendingPage/PendingPage";
import Footer from "./components/Footer/Footer";

function App() {


  return (
    <div className="App">
      <HeaderPublic/>
        <main>
            <Registration/>
        </main>
    </div>
  )
}

export default App

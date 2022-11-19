
import "./HeaderPublic.sass"
import headerLogo from '../../assets/Logo.svg'
import logInLogo from '../../assets/log-in.svg'

function HeaderPublic() {



    return(

        <div className={"header"}>
            <img src={headerLogo} alt="logo"></img>
            <button>
                Личный кабинет
                <img src={logInLogo} alt="log-in"/>
            </button>
        </div>)

}

export default HeaderPublic

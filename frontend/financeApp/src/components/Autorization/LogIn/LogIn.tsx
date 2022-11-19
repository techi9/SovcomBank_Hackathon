import "../Autorization.sass"
import "../../../styles/App.sass"
import PhoneInput from 'react-phone-input-2'
import 'react-phone-input-2/lib/style.css'
import {useState} from "react";
import PhoneField from "../../PhoneField/PhoneField";


function LogIn() {

    let phone: string = ''
    let confirmedPhone, confirmedPassword
    const setPhone = (newPhone: string) => {
        phone = newPhone
    }

    const submit = (e: any) => {
        e.preventDefault()

        if(status === 'phone') {
            if (phone.length === 11) {
                confirmedPhone = phone
                setStatus('password')
                console.log("phone accepted" ,confirmedPhone)
            }
            else {
                setPhoneError(true)
                setTimeout(() => setPhoneError(false), 3000)
            }
        }
        else if (status === 'password') {
            confirmedPassword = password
            console.log("password " ,confirmedPassword)
            // TODO: redirect to main page
        }


    }


    const [phoneError, setPhoneError] = useState(false)
    const [status, setStatus] = useState("phone")
    const [password, setPassword] = useState("")

    return(

        <div className="pane">
            <h1> Вход в личный кабинет</h1>
            <section>
                {status === 'password' ?
                    <>
                        <h2>Введите пароль</h2>
                        <input type={"password"} value={password} onChange={e => setPassword(e.target.value)}/>
                    </>
                    :
                    <>
                        <h2>Телефон</h2>
                        <PhoneField setPhone={setPhone}/>
                        <p style={!phoneError ? {visibility: 'hidden'} : {}} className="extra-info-error ">Введен неверный номер телефона</p>
                    </>}

            </section>
              <button className={"resume-button"} onClick={submit}>ПРОДОЛЖИТЬ</button>
            <p className="extra-info">
                После ввода телефона, нажмите "Продолжить". Вы будете перенаправлены на защищенную страницу для продолжения ввода авторизационных данных
            </p>

            <p className="extra-info">
                Нет аккаунта? <a>Зарегистрироваться</a>
            </p>

        </div>)

}

export default LogIn

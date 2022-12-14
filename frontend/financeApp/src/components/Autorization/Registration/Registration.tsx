import "../Autorization.sass"
import "../../../styles/App.sass"
import "./Registration.sass"
import {useState} from "react";
import PhoneField from "../../PhoneField/PhoneField";
import {RegistrationDto} from "../../../api/dto";
import axios from '../../../api/axious';
import {useLocation, useNavigate} from "react-router-dom";


function Registration() {

    // const navigate = useNavigate();
    // const location = useLocation();
    // const from = location.state?.from?.pathname || "/";

    const [surname, setSurname] = useState("")
    const [name, setName] = useState("")
    const [middleName, setMiddleName] = useState("")
    const [dateOfBirth, setDateOfBirth] = useState("")
    const [passportSeries, setPassportSeries] = useState("")

    let phone: string
    let confirmedPhone: string, confirmedPassword
    const setPhone = (newPhone: string) => {
        phone = newPhone
    }


    const [phoneError, setPhoneError] = useState(false)
    const [status, setStatus] = useState("basic-data")

    const [stateCounter, setStateCounter] = useState(1)

    const [mail, setMail] = useState("")
    const [password, setPassword] = useState("")
    const [passwordRepeat, setPasswordRepeat] = useState("")

    const submit = async (e: any) => {
        e.preventDefault()

        if(status === 'basic-data') {
            if (dateOfBirth.match(/^\d{2}\.\d{2}\.\d{4}$/) &&
                name.length>0 && surname.length>0 && middleName.length>0 &&
                passportSeries.length > 0 && dateOfBirth.length > 0) {
                setStatus('contact-data')
                setStateCounter(stateCounter + 1)
                console.log("basic data accepted")
            }
        }
        else if (status === 'contact-data') {

            console.log(phone)
            console.log(confirmedPhone, "confirmedPassword")

            if (phone?.length === 11 || confirmedPhone) {
                console.log("phone accepted")
                confirmedPhone = phone
                console.log(confirmedPhone, "confirmedPassword")
                if(mail.length > 0){
                    setStatus('password')
                }
                setStateCounter(stateCounter + 1)

            }
            else {
                setPhoneError(true)
                setTimeout(() => setPhoneError(false), 3000)
            }
        }
        else if (status === 'password') {
            if (password.length > 0 && password === passwordRepeat) {
                confirmedPassword = password
                console.log("password confirmed", confirmedPassword)
                setStatus('done')
            }
        }
        else if (status === 'done') {
            confirmedPassword = password
            console.log("password " ,confirmedPassword)
            let registrationData: RegistrationDto = {
                surname: surname,
                name: name,
                middleName: middleName,
                dateOfBirth: dateOfBirth,
                passportSeries: passportSeries,
                phone: phone,
                email: mail,
                password: confirmedPassword
            }
            const response = await axios.post("/registration",
                JSON.stringify({ registrationData }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );
            // TODO: redirect to main page and send this data
        }

    }


    let toDisplay

    if (status === 'basic-data') {
        toDisplay =
            <>
                <h2>??????????????</h2>
                <input type={"text"} value={surname} onChange={e => setSurname(e.target.value)}/>

                <h2>??????</h2>
                <input type={"text"} value={name} onChange={e => setName(e.target.value)}/>

                <h2>????????????????</h2>
                <input type={"text"} value={middleName} onChange={e => setMiddleName(e.target.value)}/>

                <h2>?????????????? ???????? ????????????????</h2>
                <input type={"text"} value={dateOfBirth} onChange={e => setDateOfBirth(e.target.value)}/>

                <h2>?????????? ?? ?????????? ????????????????</h2>
                <input type={"text"} value={passportSeries} onChange={e => setPassportSeries(e.target.value)}/>
            </>
            }
    else if (status === 'contact-data') {
        toDisplay =
            <>
                <h2>??????????</h2>
                <input type={"text"} value={mail} onChange={e => setMail(e.target.value)}/>

                <h2>??????????????</h2>
                <PhoneField setPhone={setPhone}/>
                <p style={!phoneError ? {visibility: 'hidden'} : {}} className="extra-info-error"> ???????????? ???????????????? ?????????? ???????????????? </p>
            </>
    }
    else if (status === 'password') {
        toDisplay =
            <>
                <h2>?????????????? ????????????</h2>
                <input type={"password"} value={password} onChange={e => setPassword(e.target.value)}/>

                <h2>?????????????????? ????????????</h2>
                <input type={"password"} value={passwordRepeat} onChange={e => setPasswordRepeat(e.target.value)}/>

            </>
    }

    return(

        <div className="pane">
            <div className={"form-counter"}>
                <div>
                    {stateCounter}
                </div>
                <div>/3</div>
            </div>

            <h1> ?????????????????????? </h1>
            <section>
                {toDisplay}
            </section>
            <button className={"resume-button"} onClick={submit}>????????????????????</button>


            <p className="extra-info">
                ?????????????? ???????????? ???????????????????? "????????????????????", ???? ???????????????????????? ?? <a href="#">?????????????????? ???????????????????????????????????? ??????????????????????</a>
            </p>

        </div>)

}

export default Registration

import "./PhoneField.sass"
import PhoneInput from 'react-phone-input-2'
import 'react-phone-input-2/lib/style.css'
import {useState} from "react";

interface Props {
    setPhone: (phone: string) => void
}

function PhoneField(props: Props) {


    const [phone, setPhone] = useState('+7')

    return(
            <PhoneInput
                country={'ru'}
                value={phone}
                onChange={phone => {
                    setPhone(phone)
                    props.setPhone(phone)
                }}
                disableDropdown={true}
            />)

}

export default PhoneField

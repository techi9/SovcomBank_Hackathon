import "./HeaderPrivate.sass"
import headerLogo from '../../assets/Logo.svg'
import messagePlus from '../../assets/message_plus_alt.png'
import notificationOutline from '../../assets/notification_outline_dot.png'

function HeaderPrivate() {


  return (
      <div className="header-container">
            <header>
                <div className="top">
                    <img src={headerLogo} alt="logo"/>
                    <div>
                        <img src={messagePlus} alt="message"/>
                        <img src={notificationOutline} alt="notification"/>
                    </div>
                </div>

                <div className="bot">
                    <section>Мой портфель</section>
                    <section>Торговля</section>
                    <section>История</section>
                </div>
            </header>
      </div>
  );
}

export default HeaderPrivate;
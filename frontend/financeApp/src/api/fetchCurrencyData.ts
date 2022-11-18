import ExchangeRatesRoute from "./apiRoutes";


function fetchExchangeRates() {
  return fetch(ExchangeRatesRoute, {body: JSON.stringify({id:'R01235'})}).then((response) => response.json())
}

export default fetchExchangeRates

import {useQuery} from "@tanstack/react-query";
import fetchExchangeRates from "../../api/fetchCurrencyData";

function ExchangeRates() {

    const { status ,isLoading, isError, data } = useQuery({ queryKey: ['ExchangeRates'], queryFn: fetchExchangeRates, refetchOnWindowFocus: true})


    return(
        <div className="ExchangeRates">
            <h1>Exchange Rates</h1>
            {isLoading && <p>Loading...</p>}
            {isError && <p>Error: {status}</p>}
            {data && <p>Exchange Rates: {data}</p>}
        </div>)
}

export default ExchangeRates
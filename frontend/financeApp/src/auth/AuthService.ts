import axios from "axios";
import apiUrlBase from "../api/apiRoutes";

const API_URL = apiUrlBase + "/auth/";

class AuthService {
    login(username: string, password: string) {
        return axios
            .post(API_URL + "signin", {
                username,
                password
            })
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username:string, email:string, password:string) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }

    getCurrentUser() {
        const user = localStorage.getItem('user')
        if (user) return JSON.parse(user);

    }
}

export default new AuthService();
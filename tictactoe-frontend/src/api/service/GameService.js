import httpService from "@/api/service/HttpService";

class GameService {

  async createGame(isPublic) {
    return await httpService.post('games/create', {publicGame: isPublic})
  }

  async getValidToken() {
    return await httpService.get('games/token/' + (localStorage.getItem('token') || 'unknown'))
      .then(response => {
        return response.data.token
      })
  }

  async getGameByOtp(otp) {
    return await httpService.get('games/otp/' + otp)
  }

}

export default new GameService()

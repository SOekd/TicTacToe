import httpService from "@/api/service/HttpService";

class GameService {

  async createGame(isPublic) {
    return await httpService.post('games/create', {publicGame: isPublic})
  }

  async validToken(oldToken) {
    return await httpService.get('games/token/' + oldToken)
  }

  async getGameByOtp(otp) {
    return await httpService.get('games/otp/' + otp)
  }

}

export default new GameService()

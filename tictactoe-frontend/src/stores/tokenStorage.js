import gameService from "@/api/service/GameService";
import { defineStore } from 'pinia'

export const useTokenStorage = defineStore('token-storage', {
  state: () => ({
    token: ''
  }),
  getters: {
    getToken: (state) => state.token
  },
  actions: () => ({
    async fetchToken(state) {
       await gameService.validToken(state)
         .then(response => {
           state.token = response.data.token
         })
    }
  })
})()

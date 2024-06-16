import { defineStore } from 'pinia'
import nameService from "@/api/service/NameService";

export const useNameStorage = defineStore('name-storage', {
  state: () => ({
    name: () => nameService.getRandomName()
  }),
  getters: {
    getName: (state) => state.name
  },
})()

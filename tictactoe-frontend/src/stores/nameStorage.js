import { defineStore } from 'pinia'
import nameService from "@/api/service/NameService";
import {computed} from "vue";

export const useNameStorage = defineStore('name-storage', () => {
  const playerName = ref('')

  function getPlayerName() {
    if (localStorage.getItem('playerName')) {
      playerName.value = localStorage.getItem('playerName')
      return playerName.value
    }

    playerName.value = nameService.getRandomName()
    localStorage.setItem('playerName', playerName.value)
    return playerName.value
  }

  return { getPlayerName }
})

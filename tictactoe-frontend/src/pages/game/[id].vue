<script setup>

</script>

<template>
  <v-container>
    <v-btn variant="tonal" color="red">Sair</v-btn>
  </v-container>

  <v-container>
    <v-card>
      <v-card-title>Adversario</v-card-title>
      <v-row class="pa-7">
        <v-col cols="auto">
          <h2> Nome do Corno</h2>
        </v-col>
      </v-row>
    </v-card>
  </v-container>

  <v-spacer></v-spacer>

  <v-container>
    <v-responsive aspect-ratio="1/1" height="50%">
      <v-sheet>
        Posição 1
      </v-sheet>
    </v-responsive>
    <v-row no-gutters>
      <v-col cols="4">

      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 2
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 3
        </v-btn>
      </v-col>
    </v-row>

    <v-divider></v-divider>

    <v-row no-gutters>


      <v-col cols="4">
        <v-btn variant="text">
          posição 4
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 5
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 6
        </v-btn>
      </v-col>
    </v-row>

    <v-divider></v-divider>

    <v-row no-gutters>
      <v-col cols="4">
        <v-btn variant="text">
          posição 7
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 8
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 9
        </v-btn>
      </v-col>
    </v-row>

  </v-container>

  <v-spacer></v-spacer>

  <v-container>
    <v-card>
      <v-card-title>Você</v-card-title>
      <v-row class="pa-7">
        <v-col cols="auto">
          <h2>{{ nameStorage.name }}</h2>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>


<script>
import {Client, Message} from '@stomp/stompjs';
import {defineProps, onMounted, onUnmounted, onBeforeMount} from 'vue';
import {useTokenStorage} from "@/stores/tokenStorage";
import {useNameStorage} from "@/stores/nameStorage";
import {useRoute} from "vue-router";

const route = useRoute();

const tokenStorage = useTokenStorage()
const nameStorage = useNameStorage()

const apiBaseUrl = import.meta.env.VITE_WEBSOCKET_URL;


const socket = new Client({
    brokerURL: `${apiBaseUrl}`
  }
)

socket.onConnect(() => {
  console.log('Conectado ao websocket')
})

socket.onStompError(() => {
  console.log('Erro ao conectar ao websocket')
})

onUnmounted(() => {
  socket.deactivate()
})

function sendJoinRequest() {
  const token = tokenStorage.getToken
  const playerName = nameStorage.getName

  socket.publish({
    destination: `/game-request/join/${route.params.id}`,
    body: JSON.stringify({
      token: token,
      playerName: playerName
    })
  })

}

function sendMoveRequest(x, y) {
  const token = tokenStorage.getToken

  socket.publish({
    destination: `/game-request/move/${route.params.id}`,
    body: JSON.stringify({
      playerToken: token,
      x: x,
      y: y
    })
  })

}

onBeforeMount(() => {
  tokenStorage.fetchToken().then(() => {
    console.log(tokenStorage.token)

    socket.activate();


    socket.subscribe(`/game-response/move/${route.params.id}`, data => {
      console.log("Received: " + data)
    })

    socket.subscribe(`/game-response/join/${route.params.id}`, data => {
      console.log("Joined: " + data)
    })

  })
})

</script>

<style scoped lang="sass">

</style>

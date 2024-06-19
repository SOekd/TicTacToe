<template>
  <v-sheet width="100%" height="100%">
    <v-container class="justify-center align-center fill-height pa-0">
      <v-card class="pa-6" height="60%" width="90%" color="#1A237E" rounded="lg">
        <v-card-title class="text-center">
          <h2>JUBILADOS JUBILAM JUNTOS</h2>
        </v-card-title>
        <v-container class="fill-height flex-column">
          <v-sheet color="#5C6BC0" class="pa-6" width="100%" elevation="5" rounded="lg">
            <v-row justify="center">
              <v-col class="text-center">
                <h3>ESCOLHA SEU NOME NO JOGO:</h3>
                <v-spacer></v-spacer>
                <v-text-field label="nome de usuário" variant="underlined"></v-text-field>
              </v-col>
            </v-row>
          </v-sheet>
          <v-spacer></v-spacer>
          <v-row justify="center" no-gutters style="width: 100%">
            <v-col>
              <v-btn size="x-large"
                     rounded="lg"
                     variant="flat"
                     elevation="5"
                     color="#5C6BC0"
                     width="100%"
                     @click="createNewGame">
                CRIAR PARTIDA
              </v-btn>
            </v-col>
          </v-row>
          <v-row justify="center" no-gutters style="width: 100%">
            <v-col cols="12">
              <v-btn size="x-large"
                     rounded="lg"
                     variant="flat"
                     elevation="5"
                     color="#5C6BC0"
                     width="100%"
                     @click="createNewGame">
                ENTRAR EM PARTIDA ALEATÓRIA
              </v-btn>
            </v-col>
          </v-row>
          <v-row justify="center" no-gutters style="width: 100%">
            <v-col>
              <v-dialog max-width="500"
                        transition="dialog-bottom-transition"
                        :persistent="otpDialog.persistence">
                <template v-slot:activator="{ props: activatorProps }">
                  <v-btn size="x-large"
                         rounded="lg"
                         variant="flat"
                         elevation="5"
                         width="100%"
                         color="#5C6BC0"
                         v-bind="activatorProps">
                    ENTRAR EM PARTIDA PRIVADA
                  </v-btn>
                </template>
                <template v-slot:default="{ isActive }">
                  <v-card
                    color="#1A237E"
                    rounded="lg"
                    elevation="5">
                    <v-card-title class="text-center">
                      <h2>INSIRA O SENHA DA PARTIDA</h2>
                    </v-card-title>
                    <v-card-text>
                      <v-sheet color="#5C6BC0" class="pa-6" elevation="5" rounded="lg">
                        <v-otp-input
                                     v-model="otpDialog.code"
                                     type="text"
                                     v-on:finish="checkOTPCode">
                        </v-otp-input>
                      </v-sheet>
                    </v-card-text>

                    <v-card-actions class="justify-center pa-6">
                      <v-btn
                        size="x-large"
                        rounded="lg"
                        variant="flat"
                        elevation="5"
                        color="#5C6BC0"
                        width="100%"
                        text="ENVIAR"
                        @click="isActive.value = false"
                      ></v-btn>
                    </v-card-actions>
                  </v-card>
                </template>
              </v-dialog>
            </v-col>
          </v-row>
          <v-spacer></v-spacer>
        </v-container>
      </v-card>
    </v-container>
  </v-sheet>
</template>

<style>
</style>

<script setup>
import gameService from "@/api/service/GameService";
import {useRouter} from "vue-router";
import {reactive} from "vue";

const router = useRouter()

const otpDialog = reactive({
  loading: false,
  code: '',
  persistence: false
})

function checkOTPCode() {
  otpDialog.loading = "loading";

  console.log("Otp: " + otpDialog.code)

  joinGameByOtp(otpDialog.code)

  otpDialog.persistence = true;
}

function createNewGame() {
  gameService.createGame(true).then(response => {
    router.push("/game/" + response.data.id)
  }).catch(error => {
    console.log(error)
  })
}

async function joinGameByOtp(otp) {
  gameService.getGameByOtp(otp)
    .then(response => {
      router.push("/game/" + response.data.id)
    })
    .catch(error => {
      console.log(error)
    })
}
</script>

<style scoped lang="scss">


</style>

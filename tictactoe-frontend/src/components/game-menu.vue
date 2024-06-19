<template>
  <v-sheet width="100%" height="100%" theme="dark">
    <v-container class="justify-center flex flex-column align-center fill-height pa-0">
      <v-sheet class="justify-center align-center pa-6" style="aspect-ratio: 2/3" rounded="lg">
        <v-container class="fill-height flex-column pa-0 justify-center align-center" style="max-width: ">
          <v-card-title class="text-center mb-10">
            <div class="text-md-h4 text-sm-h5  text-h6"> JUBILADOS JUBILAM JUNTOS</div>
          </v-card-title>
          <v-sheet id="v_sheet_name" class="pa-6" width="100%" elevation="5" rounded="lg">
            <v-row justify="center">
              <v-col class="text-center">
                <div class="text-md-h5
                text-sm-body-1
                text-body-2">
                  ESCOLHA SEU NOME NO JOGO:
                </div>
                <v-spacer></v-spacer>
                <v-text-field label="nome de usuário" class="text-md-h4 text-body-2"
                              variant="underlined"></v-text-field>
              </v-col>
            </v-row>
          </v-sheet>
          <v-spacer></v-spacer>
          <v-row class="justify-center pt-6 pb-3" no-gutters style="width: 100%">
            <v-col style="height: 100%">
              <v-btn id="menuButton"
                     variant="flat"
                     elevation="5"
                     rounded="lg"
                     width="100%"
                     height="100%"
                     class="cursor-pointer"
                     @click="createNewGame">
                <div class="text-md-h4
                text-sm-h5
                text-body-1">
                  CRIAR PARTIDA
                </div>
              </v-btn>
            </v-col>
          </v-row>
          <v-row class="justify-center pt-6 pb-3" no-gutters style="width: 100%">
            <v-col cols="12">
              <v-btn id="menuButton"
                     variant="flat"
                     elevation="5"
                     rounded="lg"
                     width="100%"
                     height="100%"
                     class="cursor-pointer"
                     @click="createNewGame">
                <div class="text-md-h4
                text-sm-h5
                text-body-1">
                  ENTRAR EM PARTIDA PRIVADA
                </div>
              </v-btn>
            </v-col>
          </v-row>
          <v-row class="justify-center pt-6 pb-3" no-gutters style="width: 100%">
            <v-col>
              <v-dialog max-width="500"
                        transition="dialog-bottom-transition"
                        :persistent="otpDialog.persistence">
                <template v-slot:activator="{ props: activatorProps }">
                  <v-btn id="menuButton"
                         variant="flat"
                         v-bind="activatorProps"
                         elevation="5"
                         rounded="lg"
                         width="100%"
                         height="100%"
                         class="cursor-pointer"
                  >
                    <div class="text-md-h4
                    text-sm-h5
                    text-body-1">
                      ENTRAR EM PARTIDA PRIVADA
                    </div>
                  </v-btn>
                </template>
                <template v-slot:default="{ isActive }">
                  <v-card
                    rounded="lg"
                    elevation="5">
                    <v-card-text>
                      <v-row>
                        <v-col class="justify-center align-center text-center">
                          <div class="text-md-h4 text-sm-h5 text-body-1"> INSIRA A SENHA DA PARTIDA</div>
                          <v-otp-input :loading="otpDialog.loading"
                                       :model-value="otpDialog.code"
                                       style="aspect-ratio: 10/1"
                                       variant="underlined"
                                       v-on:finish="checkOTPCode">
                          </v-otp-input>
                        </v-col>
                      </v-row>
                    </v-card-text>

                    <v-card-actions class="justify-center pa-6">
                      <v-btn id="menuButton"
                             @click="isActive.value = false"
                      >
                        <div class="text-md-h4
                        text-sm-h5
                        text-body-1
                        cursor-pointer">
                          ENVIAR
                        </div>
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </template>
              </v-dialog>
            </v-col>
          </v-row>
          <v-spacer></v-spacer>
        </v-container>
      </v-sheet>

    </v-container>
  </v-sheet>
</template>

<style>

</style>

<script setup>
import gameService from "@/api/service/GameService";
import {useRouter} from "vue-router";

const router = useRouter()

var otpDialog = {
  loading: false,
  code: '',
  persistence: false
}

function checkOTPCode() {
  otpDialog.loading = "loading";
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
      console.log(response)
    })
    .catch(error => {
      console.log(error)
    })
}
</script>

<style scoped lang="scss">


</style>

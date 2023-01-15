<template>
  <div>
    <v-card>
      <v-card-title class="text-h5"> 내용을 입력해주세요 </v-card-title>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="$emit('close')">
          확인
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
export default {};
</script>
<style scoped></style>

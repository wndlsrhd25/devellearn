<template>
  <div>
    <v-card>
      <v-card-title class="text-h9"> 삭제가 완료되었습니다 </v-card-title>

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

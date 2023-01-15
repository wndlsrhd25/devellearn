<template>
  <div>
    <v-card>
      <v-card-text class="text-h6"> 북마크가 등록되었습니다! </v-card-text>

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
export default {
  methods: {},
};
</script>
<style scoped></style>

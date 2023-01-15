<template>
  <v-carousel cycle height="400" hide-delimiter-background :show-arrows="false">
    <v-carousel-item
      v-for="(item, i) in items"
      :key="i"
      :src="'/api/images/banner/' + item.src"
    >
    </v-carousel-item>
  </v-carousel>
</template>
<script>
export default {
  props: ["items"],
};
</script>
<style scoped></style>

<template>
  <b-carousel
    id="carousel-1"
    v-model="slide"
    :interval="3000"
    controls
    indicators
    background="#ababab"
    img-width="100%"
    img-height="280px"
    style="text-shadow: 1px 1px 2px #333"
    @sliding-start="onSlideStart"
    @sliding-end="onSlideEnd"
    label-next=""
    label-prev=""
  >
    <!-- Slides with img slot -->
    <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
    <b-carousel-slide>
      <template #img>
        <img
          class="d-block w-100"
          width="100%"
          height="280"
          src="https://picsum.photos/1024/280/?image=55"
          alt="image slot"
        />
      </template>
    </b-carousel-slide>
    <b-carousel-slide>
      <template #img>
        <img
          class="d-block w-100"
          width="100%"
          height="280"
          src="https://picsum.photos/1024/280/?image=52"
          alt="image slot"
        />
      </template>
    </b-carousel-slide>
    <b-carousel-slide>
      <template #img>
        <img
          class="d-block w-100"
          width="100%"
          height="280"
          src="https://picsum.photos/1024/280/?image=56"
          alt="image slot"
        />
      </template>
    </b-carousel-slide>
  </b-carousel>
</template>

<script>
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
  },
};
</script>

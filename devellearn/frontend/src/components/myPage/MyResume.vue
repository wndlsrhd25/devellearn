<template>
  <div id="container" style="min-height: 100vh">

    <!-- <SideBar></SideBar> -->
    <!-- ●●●●●부트스트랩 -->

    <v-dialog v-model="savedialog" max-width="290">
      <Save @close="savedialog = false"></Save>
    </v-dialog>

    <v-dialog v-model="updatedialog" max-width="290">
      <Update @close="updatedialog = false"></Update>
    </v-dialog>

    <v-dialog v-model="deletedialog" max-width="290">
      <Delete @close="deletedialog = false"></Delete>
    </v-dialog>

    <div id="subTitle">
      <h3 style="color: #00336d">나의 이력서</h3>
    </div>
    <hr />

    <b-tab>
      <component></component>
    </b-tab>

    <div id="container">
      <b-tabs content-class="mt-3">
        <!-- 1. 인적사항기재 -->
        <b-tab title="기본사항" active>
          <div class="infoBox">
            <div id="category">기본사항</div>
            <div id="inputF">
              <div class="flex-container">
                <b-form inline @submit="onSubmit" @reset="onReset" v-if="show">
                  <div id="test1">
                    <div>
                      <b-form-group id="left" label="이름(실명)" label-for="input-1" class="testlabel">
                        <b-form-input id="input-1" v-model="selectData.name" type="email" readonly></b-form-input>
                      </b-form-group>
                    </div>
                    <div>
                      <b-form-group id="left" label="핸드폰번호" label-for="input-2" class="testlabel">
                        <b-form-input id="input-2" v-model="selectData.phone" type="email" readonly></b-form-input>
                      </b-form-group>
                    </div>
                  </div>

                  <div id="test2">
                    <div>
                      <b-form-group id="left" label="이메일" label-for="input-3">
                        <b-form-input id="input-3" v-model="selectData.email" readonly></b-form-input>
                      </b-form-group>
                    </div>
                  </div>

                  <div id="test3">
                    <div id="test4">
                      <b-form-group id="left" label="주소" label-for="input-4">
                        <div id="test7">
                          <b-input-group>
                            <b-form-input id="roadAddress" type="text" v-model="selectData.address"
                              placeholder="주소를 검색하세요">
                            </b-form-input>

                            <input type="hidden" id="postcode" placeholder="우편번호" />
                            <input type="hidden" id="jibunAddress" placeholder="지번주소" />
                            <span id="guide" style="
                                  color: #000;
                                  display: none;
                                  font-size: small;
                                "></span>
                            <input type="hidden" id="extraAddress" placeholder="참고항목" />

                            <b-input-group-append>
                              <b-button variant="primary" stlye="" @click="searchAddr">search</b-button>
                            </b-input-group-append>
                          </b-input-group>
                        </div>
                      </b-form-group>
                    </div>
                    <div id="test7" style="margin-left: 103px">
                      <b-form-group>
                        <b-form-input id="detailAddress" style="width: 300px; margin-left: -2px;" type="text"
                          placeholder="상세주소" v-model="selectData.addressD" required>
                        </b-form-input>
                      </b-form-group>
                    </div>
                  </div>

                  <div id="plusBtn">
                    <b-button block variant="primary" style="
                          width: 855px;
                          background-color: crimson;
                          border: none;
                        " @click="updateAddr">기본사항 저장
                    </b-button>
                  </div>
                </b-form>
              </div>
            </div>
          </div>
        </b-tab>

        <b-tab title="경력사항">
          <!-- 2. 경력사항 기재 -->
          <form>
            <div class="infoBox">
              <div id="category">경력</div>
              <div id="inputF">
                <!-- 부트스트랩 추가중 -->
                <div class="flex-container">
                  <b-form id="careerForm" inline @submit="onSubmit" @reset="onReset" v-if="show">
                    <div id="test1">
                      <b-form-group id="left" label="회사명" label-for="input-5">
                        <b-form-input id="input-5" name="company" v-model="companyName" type="text" required>
                        </b-form-input>
                      </b-form-group>
                      <b-form-group id="left" label="부서명" label-for="input-6">
                        <b-form-input id="input-6" name="dept" v-model="dept" type="text" required></b-form-input>
                      </b-form-group>
                    </div>
                    <div id="test1">
                      <b-form-group id="left" label="담당업무" label-for="char4">
                        <b-form-input id="char4" name="task" v-model="task" placeholder="담당업무 입력" required>
                        </b-form-input>
                      </b-form-group>
                      <b-form-group id="left" label="직책" label-for="char2">
                        <b-form-input id="char2" name="position" v-model="position" placeholder="직책 입력" required>
                        </b-form-input>
                      </b-form-group>
                    </div>
                    <div id="test1">
                      <b-form-group id="left" label="근무유형" label-for="char4">
                        <b-form-select id="char4" name="jobType" v-model="jobType" :options="job_types"
                          class="select_job_type">
                        </b-form-select>
                      </b-form-group>
                      <b-form-group id="left" label="재직여부" label-for="input-10">
                        <b-form-select id="char4" name="jobStatus" v-model="jobStatus" :options="job_statuss"
                          class="select_job_status">
                        </b-form-select>
                      </b-form-group>
                    </div>
                    <div id="test8">
                      <b-form-group id="jobPeriod" label="근무 기간" label-for="input-10" name="jobPeriod">
                        <el-date-picker id="char4" type="monthrange" value-format="yyyy-MM" format="yyyy-MM"
                          placeholder="  " range-separator=" ~ " v-model="form.month" :picker-options="pickerOptions"
                          unlink-panels>
                        </el-date-picker>
                      </b-form-group>
                    </div>

                    <!-- 경력사항 파일 첨부  -->
                    <div id="file-message">
                      <h6>
                        ▼경력내역 파일첨부 시, 경력 인증 신청이 완료됩니다.
                      </h6>
                    </div>
                    <b-form-file v-model="certiFile" name="imgFiles" id="certiFile" class="mt-3" plain></b-form-file>
                    <!-- <div class="mt-3">
                        Selected file: {{ file2 ? file2.name : "" }}
                      </div> -->

                    <div id="plusBtn">
                      <b-button block variant="primary" @click="insertCareer()" style="
                            width: 855px;
                            background-color: orangered;
                            border: none;
                          ">경력사항 추가
                      </b-button>
                    </div>
                  </b-form>

                </div>
              </div>
            </div>
          </form>



          <div v-if="careerData.length!=''">
            <p5>▼ 등록된 경력사항은 다음과 같습니다.▼</p5>


            <form v-for="career in careerData" :key="career.careerNo" style="
                border: 1px dashed #bcbcbc;
                padding: 30px 0px;
                margin: 10px 0px;
                ">



              <!-- 글번호 : {{ career.careerNo }} -->
              <div class="inner-infoBox">
                <div id="category">경력</div>
                <div id="inputF">
                  <!-- 부트스트랩 추가중 -->
                  <div class="flex-container">
                    <b-form :id="career.careerNo + 'careerForm'" inline @submit="onSubmit" @reset="onReset" v-if="show">
                      <div id="test1">
                        <b-form-group id="left" label="회사명" label-for="input-5">
                          <b-form-input id="input-5" name="company" v-model="career.company" type="text" required>
                          </b-form-input>
                        </b-form-group>
                        <b-form-group id="left" label="부서명" label-for="input-6">
                          <b-form-input id="input-6" name="dept" v-model="career.dept" type="text" required>
                          </b-form-input>
                        </b-form-group>
                      </div>

                      <div id="test1">
                        <b-form-group id="left" label="담당업무" label-for="char4">
                          <b-form-input id="char4" name="task" v-model="career.task" placeholder="담당업무 입력" required>
                          </b-form-input>
                        </b-form-group>
                        <b-form-group id="left" label="직책" label-for="char2">
                          <b-form-input id="char2" name="position" v-model="career.position" placeholder="직책 입력"
                            required>
                          </b-form-input>
                        </b-form-group>
                      </div>

                      <div id="test1">
                        <b-form-group id="left" label="근무유형" label-for="char4">
                          <b-form-select id="char4" name="jobType" v-model="career.jobType" :options="job_types"
                            class="select_job_type">
                          </b-form-select>
                        </b-form-group>
                        <b-form-group id="left" label="재직여부" label-for="char2">
                          <b-form-select id="char4" name="jobStatus" v-model="career.jobStatus" :options="job_statuss"
                            class="select_job_status">
                          </b-form-select>
                        </b-form-group>
                      </div>

                      <!-- 근무기간  -->
                      <div id="test8">
                        <b-form-group id="jobPeriod" label="근무기간" label-for="input-10" name="jobPeriod">
                          <el-date-picker id="char4" style="margin-left: 43px;" type="monthrange" name="jobPeriod"
                            value-format="yyyy-MM" format="yyyy-MM" placeholder="    " range-separator=" "
                            v-model="career.jobPeriod" :picker-options="pickerOptions" unlink-panels>
                          </el-date-picker>
                        </b-form-group>
                      </div>

                      <!-- 경력사항 파일 첨부  -->

                      <div id="file-message">
                        <h6>
                          ▼경력내역 파일첨부 시, 경력 인증 신청이 완료됩니다.
                        </h6>
                      </div>
                      <b-form-file v-model="selectData.certiFile" name="imgFiles" id="certiFile" class="mt-3" plain>
                      </b-form-file>
                      <div class="mt-3">
                        Selected file: {{ file2 ? file2.name : "" }}
                      </div>
                    </b-form>
                  </div>
                </div>
              </div>
              <div style="text-align: right; margin-right: 10px; margin-top:-30px;   ">
                <a href="javascript:void(0)" @click="updateCareer(career.careerNo)" class="card-link">수정</a>
                <a href="javascript:void(0)" @click="deleteCareer(career.careerNo)" class="card-link">삭제</a>
              </div>



            </form>
          </div>

        </b-tab>

        <b-tab title="프로젝트">
          <!-- 3. 프로젝트 기재 -->
          <div class="infoBox">
            <div id="category">프로젝트</div>
            <div id="inputF">
              <div class="flex-container">
                <b-form id="projectForm" inline @submit="onSubmit" @reset="onReset" v-if="show">
                  <div id="test1">
                    <b-form-group id="left" label="프로젝트명" label-for="input-5">
                      <b-form-input id="input-7" name="projectName" v-model="projectName" style="width:350px"
                        type="text" required>
                      </b-form-input>
                    </b-form-group>
                  </div>

                  <div id="p_content">
                    <div id="contents">
                      <b-form-group id="left" label="프로젝트 내용" label-for="input-6"></b-form-group>
                    </div>
                    <b-form-textarea name="projectContent" v-model="projectContent" placeholder="Enter something..."
                      rows="3" max-rows="6" style="width: 700px">
                    </b-form-textarea>

                  </div>

                  <div id="test1">
                    <b-form-group id="left" label="프로젝트 링크" label-for="input-5">
                      <b-form-input style="
                            margin-left: 18px;
                            margin-top: 15px;
                            width: 350px;
                          " id="projectLink" name="projectLink" v-model="projectLink" type="text" required>
                      </b-form-input>
                    </b-form-group>
                  </div>

                  <div id="test8">
                    <b-form-group id="period" label="프로젝트 기간" label-for="input-10" name="period">
                      <el-date-picker style="margin-left: 19px; margin-top: 10px" id="period" name="period"
                        type="monthrange" value-format="yyyy-MM" format="yyyy-MM" placeholder="    " range-separator=" "
                        v-model="period" :picker-options="pickerOptions" unlink-panels>
                      </el-date-picker>
                    </b-form-group>
                  </div>

                  <div id="plusBtn">
                    <b-button block variant="primary" @click="insertMyProj" style="
                          width: 855px;
                          background-color: orange;
                          border: none;
                        ">프로젝트 추가
                    </b-button>
                  </div>
                </b-form>
              </div>
            </div>
          </div>


          <div v-if="projectList.length!=''">
            <p5>▼ 등록된 프로젝트 항목은 다음과 같습니다.▼</p5>



            <form v-for="proj in projectList" :key="proj.projectNo" style="
                border: 1px dashed #bcbcbc;
                padding: 30px 0px;
                margin: 10px 0px;
                ">

              <div class="infoBox">
                <div id="category">프로젝트</div>
                <div id="inputF">
                  <div class="flex-container">


                    <b-form :id="proj.projectNo + 'projectForm'" inline @submit="onSubmit" @reset="onReset" v-if="show">
                      <div id="test1">
                        <b-form-group id="left" label="프로젝트명" label-for="input-5">
                          <b-form-input id="input-7" name="projectName" v-model="proj.projectName" type="text" required>
                          </b-form-input>
                        </b-form-group>
                      </div>

                      <div id="p_content">
                        <div id="contents">
                          <b-form-group id="left" label="프로젝트 내용" label-for="input-6"></b-form-group>
                        </div>
                        <b-form-textarea id="projectContent" name="projectContent" v-model="proj.projectContent"
                          placeholder="Enter something..." rows="3" max-rows="6" style="width: 700px">
                        </b-form-textarea>
                        <pre class="mt-3 mb-0">{{ text }}</pre>
                      </div>

                      <div id="test1">
                        <b-form-group id="left" label="프로젝트 링크" label-for="input-5">
                          <b-form-input style="
                              margin-left: 18px;
                              margin-top: 10px;
                              width:350px;
                            " id="projectLink" name="projectLink" v-model="proj.projectLink" type="text" required>
                          </b-form-input>
                        </b-form-group>
                      </div>

                      <div id="test8">
                        <b-form-group id="period" label="프로젝트 기간" label-for="input-10" name="period">
                          <el-date-picker style="margin-left:  19px; margin-top: 10px" id="period" name="period"
                            type="monthrange" value-format="yyyy-MM" format="yyyy-MM" placeholder="    "
                            range-separator=" " v-model="proj.period" :picker-options="pickerOptions" unlink-panels>
                          </el-date-picker>
                        </b-form-group>
                      </div>
                    </b-form>

                    <div style="text-align: right; margin-right: -160px; margin-top: -20px;">
                      <a href="javascript:void(0)" @click="updateProj(proj.projectNo)" class="card-link">수정</a>
                      <a href="javascript:void(0)" @click="deleteProj(proj.projectNo)" class="card-link">삭제</a>
                    </div>

                  </div>
                </div>
              </div>
            </form>
          </div>
        </b-tab>

        <b-tab title="교육사항">
          <!-- 4. 교육사항 기재 -->
          <div class="infoBox">
            <div id="category">교육사항</div>
            <div id="inputF">
              <!-- 부트스트랩 추가중 -->
              <div class="flex-container">
                <b-form id="eduForm" inline @submit="onSubmit" @reset="onReset" v-if="show">
                  <div id="test1">
                    <b-form-group id="left" label="교육기관명" label-for="input-5">
                      <b-form-input id="eduInsti" class="eduInstitution" name="eduInsti" v-model="eduInsti" type="text"
                        required>
                      </b-form-input>
                    </b-form-group>
                  </div>

                  <div id="p_content">
                    <div id="contents">
                      <b-form-group id="left" label="교육명" label-for="input-6"></b-form-group>
                    </div>
                    <b-form-textarea id="eduTitle" class="educationName" name="eduTitle" v-model="eduTitle"
                      placeholder="Enter something..." rows="3" max-rows="6">
                    </b-form-textarea>
                    <pre class="mt-3 mb-0">{{ text }}</pre>
                  </div>
                  <div id="test1">
                    <b-form-group id="left" label="교육 상태" label-for="char4">
                      <b-form-select id="eduStatus" class="educationStatus" style="width:350px" name="eduStatus"
                        v-model="eduStatus" :options="edu_status">
                      </b-form-select>
                    </b-form-group>
                  </div>
                  <div id="test8">
                    <b-form-group id="period" label="교육기간" label-for="input-10" name="period">
                      <el-date-picker id="eduPeriod" class="educationPeriod" name="eduPeriod" type="monthrange"
                        style="margin-left: 51px;" value-format="yyyy-MM" format="yyyy-MM" placeholder="    "
                        range-separator=" " v-model="eduPeriod" :picker-options="pickerOptions" unlink-panels>
                      </el-date-picker>
                    </b-form-group>
                  </div>

                  <div id="plusBtn">
                    <b-button block variant="primary" @click="insertEdu" style="
                          width: 855px;
                          background-color: green;
                          border: none;
                        ">교육사항 추가</b-button>
                  </div>
                </b-form>
              </div>
            </div>
          </div>


          <div v-if="eduList.length!=''">
            <p5>▼ 등록된 교육 항목은 다음과 같습니다.▼</p5>



            <form v-for="edu in eduList" :key="edu.eduNo" style="
                border: 1px dashed #bcbcbc;
                padding: 30px 0px;
                margin: 10px 0px;
                ">

              <div class="infoBox">
                <div id="category">교육사항</div>
                <div id="inputF">
                  <b-form :id="edu.eduNo + 'eduForm'" inline @submit="onSubmit" @reset="onReset" v-if="show">
                    <div id="test1">
                      <b-form-group id="left" label="교육기관명" label-for="input-5">
                        <b-form-input id="eduInsti" name="eduInsti" class="eduInstitution" v-model="edu.eduInsti"
                          type="text" required>
                        </b-form-input>
                      </b-form-group>
                    </div>

                    <div id="p_content">
                      <div id="contents">
                        <b-form-group id="left" label="교육명" label-for="input-6"></b-form-group>
                      </div>
                      <b-form-textarea id="eduTitle" name="eduTitle" class="educationName" v-model="edu.eduTitle"
                        placeholder="Enter something..." rows="3" max-rows="6">
                      </b-form-textarea>
                      <pre class="mt-3 mb-0">{{ text }}</pre>
                    </div>
                    <div id="test1">
                      <b-form-group id="left" label="교육 상태" label-for="char4">
                        <b-form-select id="eduStatus" name="eduStatus" class="educationStatus" style="width:350px"
                          v-model="edu.eduStatus" :options="edu_status">
                        </b-form-select>
                      </b-form-group>
                    </div>
                    <div id="test8">
                      <b-form-group id="period" label="교육기간" label-for="input-10" name="period">
                        <el-date-picker id="eduPeriod" class="educationPeriod" name="eduPeriod" type="monthrange"
                          value-format="yyyy-MM" style="margin-left: 51px;" format="yyyy-MM" placeholder="    "
                          range-separator=" " v-model="edu.eduPeriod" :picker-options="pickerOptions" unlink-panels>
                        </el-date-picker>
                      </b-form-group>
                    </div>

                    <div style="text-align: right; margin-left: 480px; margin-top: -25px;">
                      <a href="javascript:void(0)" @click="updateEdu(edu.eduNo)" class="card-link">수정</a>
                      <a href="javascript:void(0)" @click="deleteEdu(edu.eduNo)" class="card-link">삭제</a>
                    </div>
                  </b-form>
                </div>
              </div>
            </form>
          </div>
        </b-tab>

        <b-tab title="자격증">
          <!-- 5. 자격증 관련 사항 기재 -->
          <div class="infoBox">
            <div id="category">자격증</div>
            <div id="inputF">
              <div class="flex-container">
                <b-form id="certiForm" inline @submit="onSubmit" @reset="onReset" v-if="show">
                  <div id="test1">
                    <b-form-group id="left" label="자격증명" label-for="input-5">
                      <b-form-input id="certiName" class="certifiname" name="certiName" v-model="certiName" type="text"
                        required>
                      </b-form-input>
                    </b-form-group>
                  </div>
                  <div id="p_content">
                    <div id="contents">
                      <b-form-group id="left" label="점수/급" label-for="input-6"></b-form-group>
                    </div>
                    <b-form-textarea id="score" name="score" v-model="score" placeholder="Enter something..." rows="3"
                      max-rows="6">
                    </b-form-textarea>
                    <pre class="mt-3 mb-0">{{ text }}</pre>
                  </div>
                  <div id="test8">
                    <b-form-group id="left" label="발급기관" label-for="input-5">
                      <b-form-input id="institution" class="institu" name="institution" v-model="institution"
                        type="text" required>
                      </b-form-input>
                    </b-form-group>

                    <b-form-group id="move_right" label="취득 월" label-for="input-10">
                      <b-form-input id="acqDate" class="getMonth" name="acqDate" type="month"></b-form-input>
                    </b-form-group>
                  </div>
                  <div id="file-message">
                    <h6>▼자격증 파일첨부 시, 자격 인증 신청이 완료됩니다.</h6>
                  </div>
                  <b-form-file v-model="certiFile" name="imgFiles" id="certiFile" class="mt-3" plain></b-form-file>
                  <div class="mt-3">
                    Selected file: {{ file2 ? file2.name : "" }}
                  </div>
                  <div id="plusBtn">
                    <b-button block variant="primary" @click="insertCerti" style="
                          width: 855px;
                          background-color: royalblue;
                          border: none;
                        ">자격증 추가</b-button>
                  </div>
                </b-form>
              </div>
            </div>
          </div>


          <div v-if="certList.length!=''">
            <p5>▼ 등록된 자격증은 다음과 같습니다.▼</p5>

            <form v-for="certi in certList" :key="certi.certiNo" style="
                border: 1px dashed #bcbcbc;
                padding: 30px 0px;
                margin: 10px 0px;
                ">

              <div class="infoBox">
                <div id="category">자격증</div>

                <div id="inputF">
                  <b-form :id="certi.certiNo + 'certiForm'" inline @submit="onSubmit" @reset="onReset" v-if="show">
                    <div id="test1">
                      <b-form-group id="left" label="자격증명" label-for="input-5">
                        <b-form-input id="certiName" class="certifiname" name="certiName" v-model="certi.certiName"
                          type="text" required>
                        </b-form-input>
                      </b-form-group>
                    </div>

                    <div id="p_content">
                      <div id="contents">
                        <b-form-group id="left" label="점수/급" label-for="input-6"></b-form-group>
                      </div>
                      <b-form-textarea id="score" name="score" v-model="certi.score" placeholder="Enter something..."
                        rows="3" max-rows="6">
                      </b-form-textarea>
                      <pre class="mt-3 mb-0">{{ text }}</pre>
                    </div>

                    <div id="test8">
                      <b-form-group id="left" label="발급기관" label-for="input-5">
                        <b-form-input id="institution" class="institu" name="institution" v-model="certi.institution"
                          type="text" required>
                        </b-form-input>
                      </b-form-group>

                      <b-form-group id="move_right" label="취득 월" label-for="input-10">
                        <b-form-input id="acqDate" class="getMonth" name="acqDate" type="month" v-model="certi.acqDate">
                        </b-form-input>
                      </b-form-group>
                    </div>

                    <div id="file-message">
                      <h6>▼자격증 파일첨부 시, 자격 인증 신청이 완료됩니다.</h6>
                    </div>
                    <b-form-file v-model="certiFile" name="imgFiles" id="certiFile" class="mt-3" plain></b-form-file>
                    <div class="mt-3">
                      Selected file: {{ file2 ? file2.name : "" }}
                    </div>
                  </b-form>
                </div>
              </div>

              <div style="text-align: right; margin-right: 20px">
                <a href="javascript:void(0)" @click="updateCerti(certi.certiNo)" class="card-link">자격 수정</a>
                <a href="javascript:void(0)" @click="deleteCerti(certi.certiNo)" class="card-link">자격 삭제</a>
              </div>

            </form>
          </div>
        </b-tab>

        <b-tab title="보유스킬">
          <!-- 6. 보유스킬 기재 -->
          <div class="infoBox">
            <div id="category">보유 스킬</div>
            <div id="inputF">
              <div id="skill-container" style="float: left; margin-left: -20%; " width>
                <b-form inline @submit="onSubmit" @reset="onReset" v-if="show">
                  <b-input-group>

                    <b-form-tags input-id="tags-basic" v-model="selectSkill" size="lg" style="width:850px;">
                    </b-form-tags>
                  </b-input-group>

                  <!-- <div style="margin: 30px">
                      <b-button block variant="primary" @click="addTag"
                        >나의 스킬 저장</b-button
                      >
                    </div> -->


                  <div id="plusBtn">
                    <b-button block variant="primary" @click="addTag" style="
                      width: 855px;
                      background-color: darkorchid;
                      border: none;
                    ">나의 스킬 저장</b-button>
                  </div>




                </b-form>
              </div>
            </div>
          </div>
        </b-tab>

        <b-tab title="자기소개서">
          <!-- 7. 자기소개-->
          <div class="infoBox">
            <div id="category">자기소개서</div>

            <div style="width :855px; margin-left:130px;">
              <b-form-textarea id="introduction" name="introduction" v-model="selectData.introduction"
                placeholder="Enter something..." rows="8" max-rows="30">
              </b-form-textarea>

              <div id="plusBtn">
                <b-button block variant="primary" @click="updateIntro" style="
                      width: 855px;
                      background-color: palevioletred;
                      border: none;
                    ">자기소개서 저장</b-button>
              </div>
            </div>
          </div>
        </b-tab>
      </b-tabs>
    </div>
  </div>

</template>

<script>
  import axios from "axios";
  import SideBar from "../common/SideBar.vue";
  import Save from "@/components/common/Save.vue";
  import Update from "@/components/common/Update.vue";
  import Delete from "@/components/common/Delete.vue";
  export default {
    components: {
      SideBar,
      Save,
      Update,
      Delete,
    },
    data() {
      return {
        savedialog: false,
        updatedialog: false,
        deletedialog: false,
        memberId: "",
        selectData: [],
        companyName: "",
        dept: "",
        task: "",
        position: "",
        jobType: "",
        jobStatus: "",
        certiFile: "",
        form: {},
        jobPeriod: "",
        careerData: [],
        projData: [],
        projectName: "",
        projectContent: "",
        projectLink: "",
        //프로젝트기간
        period: "",
        eduData: [],
        eduPeriod: "",
        //FORM 데이터를 안에서만 선언하는데 DATA에 추가해줄 필요가 있나???
        certiData: [],
        acqDate: "",
        careerList: [],
        eduList: [],
        projectList: [],
        certList: [],
        skillList: [],
        selectSkill: [],
        count: 1,
        job_type: null,
        job_types: [{
            value: null,
            projecttext: "",
            text: "근무유형을 선택하세요.",
            disabled: true,
          },
          {
            value: "인턴",
            text: "인턴",
          },
          {
            value: "계약직",
            text: "계약직",
          },
          {
            value: "정규직",
            text: "정규직",
          },
          {
            value: "개인사업",
            text: "개인사업",
          },
          {
            value: "프리랜서",
            text: "프리랜서",
          },
        ],
        job_status: null,
        job_statuss: [{
            value: null,
            text: "재직여부을 선택하세요.",
            disabled: true,
          },
          {
            value: "재직중",
            text: "재직중",
          },
          {
            value: "퇴사",
            text: "퇴사",
          },
        ],
        edu_status: [{
            value: null,
            text: "교육 상태를 선택하세요.",
            disabled: true,
          },
          {
            value: "수료중",
            text: "수료중",
          },
          {
            value: "수료",
            text: "수료",
          },
        ],
        form: {
          email: "",
          name: "",
          checked: [],
        },
        show: true,
        selected: null,
      };
    },
    created() {
      this.selectMember();
      this.selectCareer();
      this.getResumeList();
    },
    methods: {
      onSubmit(event) {
        event.preventDefault();
        alert(JSON.stringify(this.form));
      },
      onReset(event) {
        event.preventDefault();
        // Reset our form values
        this.form.email = "";
        this.form.name = "";
        this.form.checked = [];
        // Trick to reset/clear native browser form validation state
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        });
      },
      //로그인 정보로 회원정보 가져오기
      selectMember() {
        var memberId = this.$store.state.userStore.id;
        this.$axios
          .get("/api/selectMember/" + memberId)
          .then((res) => {
            console.log(res);
            this.selectData = res.data;
            const skills = this.selectData.skill.split(",");
            this.selectSkill = skills;
            console.log(this.selectSkill);
            this.$store.commit("userStore/setUserData", res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      },
      //경력사항 불러오기
      selectCareer() {
        var memberId = this.$store.state.userStore.id;
        console.log(memberId);
        this.$axios
          .get('/api/selectCareer', {
            params: {
              memberId: memberId,
            }
          })
          .then((res) => {
            console.log(res);
            this.careerData = res.data;
            component.$router.go(0);
          })
          .catch((err) => {
            console.log(err);
          });
      },
      //기존 데이터 불러오기
      getResumeList() {
        this.$axios
          .post("/api/getResumeList", {
            memberId: this.$store.state.userStore.id,
          })
          .then((resp) => {
            this.eduList = resp.data.EduList;
            this.certList = resp.data.CertList;
            this.skillList = resp.data.SkillList;
            this.projectList = resp.data.ProjectList;
            this.careerList = resp.data.CareerList;
          })
          .catch((error) => {
            console.log(error);
          });
      },
      searchAddr() {
        new window.daum.Postcode({
          oncomplete: (data) => {
            //function이 아니라 => 로 바꿔야한다.
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ""; // 참고 항목 변수
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              extraRoadAddr +=
                extraRoadAddr !== "" ?
                ", " + data.buildingName :
                data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== "") {
              extraRoadAddr = " (" + extraRoadAddr + ")";
            }
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("postcode").value = data.zonecode;
            document.getElementById("roadAddress").value = roadAddr;
            document.getElementById("jibunAddress").value = data.jibunAddress;
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if (roadAddr !== "") {
              document.getElementById("extraAddress").value = extraRoadAddr;
            } else {
              document.getElementById("extraAddress").value = "";
            }
            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if (data.autoRoadAddress) {
              var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
              guideTextBox.innerHTML = "(예상 도로명 주소 : " + expRoadAddr + ")";
              guideTextBox.style.display = "block";
            } else if (data.autoJibunAddress) {
              var expJibunAddr = data.autoJibunAddress;
              guideTextBox.innerHTML = "(예상 지번 주소 : " + expJibunAddr + ")";
              guideTextBox.style.display = "block";
            } else {
              guideTextBox.innerHTML = "";
              guideTextBox.style.display = "none";
            }
          },
        }).open();
      },
      //1.기본사항 업데이트
      updateAddr() {

        this.$axios
          .put("/api/updateAddr", {
            address: document.getElementById("roadAddress").value,
            addressD: document.getElementById("detailAddress").value,
            memberId: this.$store.state.userStore.id,
          })
          .then((res) => {
            if (res.data != null) {
              // alert("등록완료!");
              Swal.fire({
                position: "center-center",
                icon: "success",
                toast: true,
                title: "저장 완료되었습니다.",
                showConfirmButton: false,
                timer: 1000,
              }).then((result) => {
                this.selectMember();
              })
            }
          });
      },
      //2.경력사항 insert
      insertCareer() {
        const formData = new FormData(document.getElementById("careerForm"));
        formData.append("memberId", this.$store.state.userStore.id);
        formData.append(
          "jobPeriod ",
          this.form.month[0] + " " + this.form.month[1]
        );
        const component = this;
        console.log(formData);

        this.$axios
          .post("/api/insertCareer", formData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((resp) => {
            console.log(resp);
            Swal.fire({
              position: "center-center",
              icon: "success",
              toast: true,
              title: "추가 완료되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.selectCareer();
            })
          });
      },
      //2-2. 경력사항 수정(업데이트)
      updateCareer(careerNo) {
        let id = careerNo + "careerForm";
        let form = document.getElementById(id);
        const formData = new FormData(form);
        console.log(formData.get("j"));
        console.log(formData.get("o"));
        formData.append("memberId", this.$store.state.userStore.id);
        formData.append(
          "jobPeriod ",
          formData.get("j") + " " + formData.get("o")
        );
        formData.append("careerNo", careerNo);
        console.log(formData);

        this.$axios
          .put("/api/updateCareer", formData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((resp) => {
            console.log(resp);

            Swal.fire({
              position: "center-center",
              icon: "success",
              toast: true,
              title: "수정 완료되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.selectCareer();
            })
          });
      },
      //2-3. 경력사항 삭제
      deleteCareer(careerNo) {


        this.$axios

          .delete("/api/deleteCareer/" + careerNo)
          .then((resp) => {
            //this.selectCareer();
            for (let i = 0; i < this.careerData.length; i++) {
              if (this.careerData[i].careerNo == careerNo) {
                this.careerData.splice(i, 1);
              }
            }
            Swal.fire({
              position: "center-center",
              icon: "warning",
              toast: true,
              title: "삭제 완료!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      },

      // 3.마이프로젝트 인서트
      insertMyProj() {
        const projData = new FormData(document.getElementById("projectForm"));
        projData.append("memberId", this.$store.state.userStore.id);
        projData.append("period", this.period[0] + " " + this.period[1]);
        console.log(this.$store.state.userStore.id);
        console.log(this.period[0] + " " + this.period[1]);
        let sendData = {};
        projData.forEach(function (value, key) {
          sendData[key] = value;
        });
        this.$axios.post("/api/insertMyProj", sendData)
          .then((resp) => {
            console.log(resp);
            Swal.fire({
              position: "center-center",
              icon: "success",
              toast: true,
              title: "추가 완료되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.getResumeList();
            })
          });
      },
      //3-2. 프로젝트 수정
      updateProj(projectNo) {
        let id = projectNo + "projectForm";
        let form = document.getElementById(id);
        console.log(form);
        const formData = new FormData(form);
        console.log(formData);
        console.log(formData.get("p"));
        console.log(formData.get("e"));
        formData.append("memberId", this.$store.state.userStore.id);
        formData.append("period", formData.get("p") + " " + formData.get("e"));
        //formData.append("jobPeriod ", this.form.month[0] + " " + this.form.month[1]);
        formData.append("projectNo", projectNo);
        console.log(formData);
        let sendData = {};
        formData.forEach(function (value, key) {
          sendData[key] = value;
        });
        this.$axios.put("/api/updateProj", sendData).then((resp) => {
          console.log(resp);
          Swal.fire({
            position: "center-center",
            icon: "success",
            toast: true,
            title: "수정 완료되었습니다.",
            showConfirmButton: false,
            timer: 1000,
          }).then((result) => {
            this.getResumeList();
          })
        });
      },
      // 프로젝트 삭제
      deleteProj(projectNo) {

        axios
          .delete("/api/deleteProj/" + projectNo)
          .then((resp) => {
            //this.selectCareer();
            for (let i = 0; i < this.projectList.length; i++) {
              if (this.projectList[i].projectNo == projectNo) {
                this.projectList.splice(i, 1);
              }
            }
            Swal.fire({
              position: "center-center",
              icon: "warning",
              toast: true,
              title: "삭제 완료!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      //4.교육사항 인서트
      insertEdu() {
        const eduData = new FormData(document.getElementById("eduForm"));
        eduData.append("memberId", this.$store.state.userStore.id);
        eduData.append("eduPeriod", this.eduPeriod[0] + " " + this.eduPeriod[1]);
        let sendData = {};
        eduData.forEach(function (value, key) {
          sendData[key] = value;
        });
        console.log("확인" + JSON.stringify(sendData));

        
        this.$axios.post("/api/insertEdu", sendData)
          .then((resp) => {
            console.log(resp);
            Swal.fire({
              position: "center-center",
              icon: "success",
              toast: true,
              title: "추가 완료되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.getResumeList();
            })
          });
      },
      //4-2. 교육사항 수정
      updateEdu(eduNo) {
        let id = eduNo + "eduForm";
        let form = document.getElementById(id);
        console.log(id);
        console.log(form);
        const formData = new FormData(form);
        console.log(formData);
        console.log(formData.get("e"));
        console.log(formData.get("d"));
        formData.append("memberId", this.$store.state.userStore.id);
        formData.append("eduPeriod", formData.get("e") + " " + formData.get("d"));
        formData.append("eduNo", eduNo);
        console.log(formData);
        let sendData = {};
        formData.forEach(function (value, key) {
          sendData[key] = value;
        });
        this.$axios.put("/api/updateEdu", sendData).then((resp) => {
          console.log(resp);
          Swal.fire({
            position: "center-center",
            icon: "success",
            toast: true,
            title: "수정 완료되었습니다.",
            showConfirmButton: false,
            timer: 1000,
          }).then((result) => {
            this.getResumeList();
          })
        });
      },
      // 4-3. 교육사항  삭제
      deleteEdu(eduNo) {

        axios
          .delete("/api/deleteEdu/" + eduNo)
          .then((resp) => {
            //this.selectCareer();
            for (let i = 0; i < this.eduList.length; i++) {
              if (this.eduList[i].eduNo == eduNo) {
                this.eduList.splice(i, 1);
              }
            }
            Swal.fire({
              position: "center-center",
              icon: "warning",
              toast: true,
              title: "삭제 완료!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      //5.자격증 사항 인서트
      insertCerti() {
        const certiData = new FormData(document.getElementById("certiForm"));
        certiData.append("memberId", this.$store.state.userStore.id);
        console.log(certiData);
        this.$axios
          .post("/api/insertCerti", certiData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((resp) => {
            console.log(resp);
            Swal.fire({
              position: "center-center",
              icon: "success",
              toast: true,
              title: "추가 완료되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.getResumeList();
            })
          });
      },
      // 5-2. 자격사항 수정
      updateCerti(certiNo) {
        let id = certiNo + "certiForm";
        let form = document.getElementById(id);
        const formData = new FormData(form);
        formData.append("memberId", this.$store.state.userStore.id);
        formData.append("certiNo", certiNo);
        console.log(formData);
        this.$axios
          .put("/api/updateCerti", formData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((resp) => {
            console.log(resp);
            Swal.fire({
              position: "center-center",
              icon: "success",
              toast: true,
              title: "수정 완료되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.getResumeList();
            })
          });
      },
      // 5-3. 자격사항 삭제
      deleteCerti(certiNo) {

        axios
          .delete("/api/deleteCerti/" + certiNo)
          .then((resp) => {
            //this.selectCareer();
            for (let i = 0; i < this.certList.length; i++) {
              if (this.certList[i].certiNo == certiNo) {
                this.certList.splice(i, 1);
              }
            }
            Swal.fire({
              position: "center-center",
              icon: "warning",
              toast: true,
              title: "삭제 완료!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      //6. 자기소개 인서트
      updateIntro() {

        this.$axios
          .put("/api/updateIntro", {
            introduction: document.getElementById("introduction").value,
            memberId: this.$store.state.userStore.id,
          })
          .then((res) => {
            if (res.data != null) {
              Swal.fire({
                position: "center-center",
                icon: "success",
                toast: true,
                title: "추가 완료되었습니다.",
                showConfirmButton: false,
                timer: 1000,
              }).then((result) => {
                this.selectMember();
              })
            }
          });
      },
      //7. 나의 스킬 등록
      addTag() {
        let skills = "";
        for (let i = 0; i < this.selectSkill.length; i++) {
          skills += this.selectSkill[i] + ",";
        }
        this.$axios
          .put("/api/updateMySkill", {
            skill: skills.substring(0, skills.length - 1),
            memberId: this.$store.state.userStore.id,
          })
          .then((res) => {
            if (res.data != null) {
              Swal.fire({
                position: "center-center",
                icon: "success",
                toast: true,
                title: "추가 완료되었습니다.",
                showConfirmButton: false,
                timer: 1000,
              }).then((result) => {
                this.selectMember();
              })
            }
          });
      },
      //뿌려줄때는 배열 안에 하나씩 넣어야함
    },
  };
</script>
<style scoped>
  #container {
    width: 1141.8px;
  }

  .infoBox {
    width: 1141.8px;
    margin-top: 40px;
  }

  form.form-inline {
    margin-left: -160px;
  }

  /* ***************************************** */
  .el-range-editor.el-input__inner {
    margin-left: 39px;
  }

  #input-7 {
    margin-left: 38px;
    width: 350px;
  }

  /* 교육기관명 */
  .eduInstitution {
    margin-left: 36px;
    width: 350px;
  }

  /* 교육명 */
  .educationName {
    width: 350px;
    margin-left: 0px;
  }

  .educationStatus {
    width: 350px;
    margin-left: 48px;
  }

  .educationPeriod {
    width: 350px;
    margin-left: 54px;
  }

  /* ***************************************** */
  #score {
    width: 300px;
  }

  #certiName {
    width: 300px;
  }

  #institution {
    width: 300px;
  }

  .getMonth {
    margin-left: 30px;
  }

  .institu {
    margin-top: 10px;
    margin-left: 49px;
  }

  .certifiname {
    margin-left: 49px;
  }

  b-tabs {
    margin-left: 50px;
  }

  #test1 div {
    width: 500px;
    margin-bottom: 10px;
    margin-right: -5%;
  }

  #input-1,
  #input-2 {
    margin-left: 31px;
    width: 300px;
  }

  #input-3 {
    margin-left: 57px;
    width: 300px;
  }

  #input-4 {
    width: 300px;
    margin-left: 104px;
  }

  #input-5,
  #input-6 {
    margin-left: 59px;
    width: 300px;
  }

  /* 좌측메뉴 3글자 */
  #char3 {
    margin-left: 43px;
    width: 300px;
  }

  /* 좌측메뉴 2글자 */
  #char2 {
    margin-left: 73px;
    width: 300px;
  }

  /* 좌측메뉴 4글자 */
  #char4 {
    margin-left: 44px;
    width: 300px;
  }

  #input-10 {
    margin-left: 20px;
    width: 150px;
  }

  #input-11 {
    margin-left: 20px;
    width: 150px;
  }

  #test8 {
    width: 1300px;
    display: flex;
  }

  #test7 {
    margin-left: 72px;
    margin-bottom: 5px;
    width: 300px;
  }

  #test3 {
    width: 1300px;
    margin-bottom: 30px;
  }

  #test4 {
    width: 500px;
    margin-right: 30px;
  }

  #test5 {
    width: 500px;
  }

  #test2 {
    width: 855px;
    margin-bottom: 20px;
  }

  #test7 {
    display: flex;
  }

  #test2 div {
    width: 500px;
    margin-right: 10px;
  }

  #test1 {
    width: 1300px;
    display: flex;
  }

  #plusBtn {
    margin-top: 30px;
    margin-bottom: 20px;
  }

  #plusBtn2 {
    margin: 30px 0px;
  }

  #contents {
    width: 110px;
  }

  .flex-container {
    margin-left: -20%;
  }

  #subTitle {
    text-align: center;
    width: 900px;
  }

  #category {
    text-align: left;
    margin-left: 10px;
    margin-top: 10px;
    font-weight: bold;
    float: left;
  }

  #inputF {
    width: 400px;
    margin: 0 auto;
  }

  #left {
    text-align: left;
    margin-top: 5px;
  }

  #move_right {
    margin-left: 20px;
  }

  #file-message {
    margin-top: 30px;
    width: 1600px;
    color: red;
  }

  #p_content {
    display: flex;
  }

  #input-12 {
    width: 855px;
    margin-left: 29px;
    margin-bottom: 20px;
  }

  #introBigBox {
    width: 950px;
  }

  #skill-container {
    width: 950px;
    height: 300px;
    text-align: center;
  }

  .btn-line {
    display: flex;
    justify-content: space-between;
  }
</style>

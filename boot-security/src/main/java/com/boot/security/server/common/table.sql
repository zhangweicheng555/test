-- LTE_GRID_USER_NUM_CLUSTER  5分钟栅格汇聚表
create table LTE_GRID_USER_NUM_CLUSTER
(
  area_type       NVARCHAR2(20),
  floor_no        NUMBER,
  stadium_no      VARCHAR2(50),
  imei_num        NUMBER,
  user_ratio      NUMBER,
  total_user_num  NUMBER,
  statistics_time VARCHAR2(50)
)



//5分钟b域汇总表
create table "B_REGION_CLUSTER"
(
  sdate             VARCHAR2(50),
  imei_num          NUMBER,
  area_type         VARCHAR2(20),
  
  stadium_no
  
  floor_no          NUMBER,
  gender_male_num   NUMBER,
  gender_female_num NUMBER,
  gender_na_num     NUMBER,
  age_18_num        NUMBER,
  age_35_num        NUMBER,
  age_50_num        NUMBER,
  age_65_num        NUMBER,
  age_na_num        NUMBER,
  source_in_1       VARCHAR2(50),
  source_in_1_num   NUMBER,
  source_in_2       VARCHAR2(50),
  source_in_2_num   NUMBER,
  source_in_3       VARCHAR2(50),
  source_in_3_num   NUMBER,
  source_in_4       VARCHAR2(50),
  source_in_4_num   NUMBER,
  source_in_5       VARCHAR2(50),
  source_in_5_num   NUMBER,
  source_in_6       VARCHAR2(50),
  source_in_6_num   NUMBER,
  source_in_7       VARCHAR2(50),
  source_in_7_num   NUMBER,
  source_in_8       VARCHAR2(50),
  source_in_8_num   NUMBER,
  source_in_9       VARCHAR2(50),
  source_in_9_num   NUMBER,
  source_in_10      VARCHAR2(50),
  source_in_10_num  NUMBER
  
  source_out_1      VARCHAR2(50),
  source_out_1_num  NUMBER,
  source_out_2      VARCHAR2(50),
  source_out_2_num  NUMBER,
  source_out_3      VARCHAR2(50),
  source_out_3_num  NUMBER,
  source_out_4      VARCHAR2(50),
  source_out_4_num  NUMBER,
  source_out_5      VARCHAR2(50),
  source_out_5_num  NUMBER,
  source_out_6      VARCHAR2(50),
  source_out_6_num  NUMBER,
  source_out_7      VARCHAR2(50),
  source_out_7_num  NUMBER,
  source_out_8      VARCHAR2(50),
  source_out_8_num  NUMBER,
  source_out_9      VARCHAR2(50),
  source_out_9_num  NUMBER,
  source_out_10     VARCHAR2(50),
  source_out_10_num NUMBER,
  
)
package cn.miao.ncncdtestdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.miao.ncncd.api.CardiovascularApi;
import cn.miao.ncncd.api.CholesterolApi;
import cn.miao.ncncd.api.DigestiveApi;
import cn.miao.ncncd.api.EndocrineApi;
import cn.miao.ncncd.api.EyesightApi;
import cn.miao.ncncd.api.FemaleReproductiveApi;
import cn.miao.ncncd.api.HarmfulSubstancesApi;
import cn.miao.ncncd.api.ImmuneApi;
import cn.miao.ncncd.api.MaleReproductiveApi;
import cn.miao.ncncd.api.NutritionalApi;
import cn.miao.ncncd.api.RespiratoryApi;
import cn.miao.ncncd.api.SkeletalApi;
import cn.miao.ncncd.api.SkinApi;
import cn.miao.ncncd.api.UserApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.api.handle.ApiHandle;
import cn.miao.ncncd.configure.Configure;
import cn.miao.ncncd.http.entity.Cardiovascular;
import cn.miao.ncncd.http.entity.Cholesterol;
import cn.miao.ncncd.http.entity.Digestive;
import cn.miao.ncncd.http.entity.Endocrine;
import cn.miao.ncncd.http.entity.Eyesight;
import cn.miao.ncncd.http.entity.FemaleReproductive;
import cn.miao.ncncd.http.entity.HarmfulSubstances;
import cn.miao.ncncd.http.entity.Immune;
import cn.miao.ncncd.http.entity.MaleReproductive;
import cn.miao.ncncd.http.entity.Nutritional;
import cn.miao.ncncd.http.entity.Respiratory;
import cn.miao.ncncd.http.entity.Skeletal;
import cn.miao.ncncd.http.entity.Skin;
import cn.miao.ncncd.http.entity.User;
import cn.miao.ncncd.util.ToastUtil;
import cn.miao.ncncd.vm.NcncdRegisterActivity;
import cn.miao.ncncdtestdemo.adapter.TestAdapter;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

	/* 列表 */
	private ListView lvTest;
	private TestAdapter testAdapter;

	/* 列表数据 */
	private String[] datas = { "上传血糖数据", "上传健康数据", "上传血压数据", "上传运动数据", "上传睡眠数据", "上传血氧数据", "上传心率数据", "上传体温数据", "上传用户数据界面",
			"上传视力数据", "上传胆固醇数据", "上传内分泌数据", "上传心脑血管数据", "上传消化系统数据", "上传呼吸系统数据", "上传骨骼系统数据", "上传免疫系统数据", "上传男性生殖系统数据",
			"上传女性生殖系统数据", "上传营养状态数据", "上传有害物质数据", "上传皮肤系统数据","上传用户数据" };
	private Class[] activitys = new Class[] { BloodSugarActivity.class, HealthActivity.class,
			BloodPressureActivity.class, SportActivity.class, SleepActivity.class, BloodOxygenActivity.class,
			HeartRateActivity.class, TemperatureActivity.class, NcncdRegisterActivity.class };

	private List<Eyesight> eyesights;
	private List<Cholesterol> cholesterols;
	private List<Endocrine> endocrines;
	private List<Cardiovascular> cardiovasculars;
	private List<Digestive> digestives;
	private List<Respiratory> respiratories;
	private List<Skeletal> skeletals;
	private List<Immune> immunes;
	private List<MaleReproductive> maleReproductives;
	private List<FemaleReproductive> femaleReproductives;
	private List<Nutritional> nutritionals;
	private List<HarmfulSubstances> harmfulSubstances;
	private List<Skin> skins;
	private User user;

	private Random random;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initBoot();
		initView();
		initData();
		initEvent();

	}

	public void initBoot() {

		Configure.init(this);

		random = new Random();

		eyesights = new ArrayList<Eyesight>();
		cholesterols = new ArrayList<Cholesterol>();
		endocrines = new ArrayList<Endocrine>();
		cardiovasculars = new ArrayList<Cardiovascular>();
		digestives = new ArrayList<Digestive>();
		respiratories = new ArrayList<Respiratory>();
		skeletals = new ArrayList<Skeletal>();
		immunes = new ArrayList<Immune>();
		maleReproductives = new ArrayList<MaleReproductive>();
		femaleReproductives = new ArrayList<FemaleReproductive>();
		nutritionals = new ArrayList<Nutritional>();
		harmfulSubstances = new ArrayList<HarmfulSubstances>();
		skins = new ArrayList<Skin>();
		user = new User();
	}

	public void initView() {

		lvTest = (ListView) findViewById(R.id.lv_test);
	}

	public void initData() {

		testAdapter = new TestAdapter(this, datas);
		lvTest.setAdapter(testAdapter);

		/* 视力 */
		for (int i = 0; i < 2; i++) {
			Eyesight eyesight = new Eyesight();
			eyesight.setLeftEye(random.nextInt(50));
			eyesight.setRightEye(random.nextInt(50));
			eyesight.setSampleTime(1516788245);
			eyesights.add(eyesight);

		}
		/* 胆固醇 */
		for (int i = 0; i < 2; i++) {
			Cholesterol cholesterol = new Cholesterol();
			cholesterol.setHighDensityProtein(random.nextInt(50));
			cholesterol.setLowDensityProtein(random.nextInt(50));
			cholesterol.setTotalCholesterol(random.nextInt(50));
			cholesterol.setTriglycerides(random.nextInt(50));
			cholesterol.setUric(random.nextInt(50));
			cholesterol.setSampleTime(1516788245);
			cholesterols.add(cholesterol);

		}
		/* 内分泌 */
		for (int i = 0; i < 2; i++) {
			Endocrine endocrine = new Endocrine();
			endocrine.setAdrenaline(random.nextInt(50));
			endocrine.setInsulin(random.nextInt(50));
			endocrine.setPineal(random.nextInt(50));
			endocrine.setThyroxine(random.nextInt(50));
			endocrine.setSampleTime(1516788245);
			endocrines.add(endocrine);

		}
		/* 心脑血管 */
		for (int i = 0; i < 2; i++) {
			Cardiovascular cardiovascular = new Cardiovascular();
			cardiovascular.setBloodLipid(random.nextInt(50));
			cardiovascular.setBrainSupplyBlood(random.nextInt(50));
			cardiovascular.setCerebralVascularElasticity(random.nextInt(50));
			cardiovascular.setCholesterolCrystallization(random.nextInt(50));
			cardiovascular.setCoronaryElasticity(random.nextInt(50));
			cardiovascular.setHeartbeatOutput(random.nextInt(50));
			cardiovascular.setMinuteOutput(random.nextInt(50));
			cardiovascular.setMyocardialBlood(random.nextInt(50));
			cardiovascular.setMyocardialOxygen(random.nextInt(50));
			cardiovascular.setPulmonaryArterialPressure(random.nextInt(50));
			cardiovascular.setVascularElasticity(random.nextInt(50));
			cardiovascular.setVascularResistance(random.nextInt(50));
			cardiovascular.setSampleTime(1516788245);
			cardiovasculars.add(cardiovascular);

		}
		/* 消化系统 */
		for (int i = 0; i < 2; i++) {
			Digestive digestive = new Digestive();
			digestive.setGastricAbsorption(random.nextInt(50));
			digestive.setGastricPeristalsis(random.nextInt(50));
			digestive.setLiverFat(random.nextInt(50));
			digestive.setLiverRed(random.nextInt(50));
			digestive.setLiverFat(random.nextInt(50));
			digestive.setProteinMetabolism(random.nextInt(50));
			digestive.setSmallIntestineAbsorption(random.nextInt(50));
			digestive.setSmallIntestinePeristalsis(random.nextInt(50));
			digestive.setSampleTime(1516788245);
			digestives.add(digestive);

		}
		/* 呼吸系统 */
		for (int i = 0; i < 2; i++) {
			Respiratory respiratory = new Respiratory();
			respiratory.setAirwayResistance(random.nextInt(50));
			respiratory.setCapacity(random.nextInt(50));
			respiratory.setSampleTime(1516788245);
			respiratories.add(respiratory);

		}

		/* 骨骼系统 */
		for (int i = 0; i < 2; i++) {
			Skeletal skeletal = new Skeletal();
			skeletal.setBoneAge(random.nextInt(50));
			skeletal.setBoneDensity(random.nextInt(50));
			skeletal.setCalciumLost(random.nextInt(50));
			skeletal.setCervicalCalcification(random.nextInt(50));
			skeletal.setFractureRisk(random.nextInt(50));
			skeletal.setHyperplasia(true);
			skeletal.setLumbarCalcification(random.nextInt(50));
			skeletal.setOsteoporosis(true);
			skeletal.setUltrasonicSpeed(random.nextInt(50));
			skeletal.setRheumatismCoefficient(random.nextInt(50));
			skeletal.setSampleTime(1516788245);
			skeletals.add(skeletal);

		}
		/* 免疫 */
		for (int i = 0; i < 2; i++) {
			Immune immune = new Immune();
			immune.setDigestiveImmuneIndex(random.nextInt(50));
			immune.setImmunoglobulinIndex(random.nextInt(50));
			immune.setLymphIndex(random.nextInt(50));
			immune.setRespiratoryImmuneIndex(random.nextInt(50));
			immune.setSpleenIndex(random.nextInt(50));
			immune.setTonsilImmuneIndex(random.nextInt(50));
			immune.setSampleTime(1516788245);
			immunes.add(immune);

		}
		/* 男性生殖系统 */
		for (int i = 0; i < 2; i++) {
			MaleReproductive maleReproductive = new MaleReproductive();
			maleReproductive.setErectileConductivity(random.nextInt(50));
			maleReproductive.setGonadotropin(random.nextInt(50));
			maleReproductive.setProstateHyperplasia(random.nextInt(50));
			maleReproductive.setProstatitis(true);
			maleReproductive.setProstatitisCalcification(random.nextInt(50));
			maleReproductive.setTestosterone(random.nextInt(50));
			maleReproductive.setSampleTime(1516788245);
			maleReproductives.add(maleReproductive);

		}
		/* 女性生殖系统 */
		for (int i = 0; i < 2; i++) {
			FemaleReproductive femaleReproductive = new FemaleReproductive();
			femaleReproductive.setAdnexitisCoefficient(random.nextInt(50));
			femaleReproductive.setEndocrineImbalanceCoefficient(random.nextInt(50));
			femaleReproductive.setVaginitisCoefficient(random.nextInt(50));
			femaleReproductive.setSampleTime(1516788245);
			femaleReproductives.add(femaleReproductive);

		}
		/* 营养状态 */
		for (int i = 0; i < 2; i++) {
			Nutritional nutritional = new Nutritional();
			nutritional.setAmino(random.nextInt(50));
			nutritional.setCalcium(random.nextInt(50));
			nutritional.setCoenzymeQ10(random.nextInt(50));
			nutritional.setIron(random.nextInt(50));
			nutritional.setFolate(random.nextInt(50));
			nutritional.setSelenium(random.nextInt(50));
			nutritional.setVitaminA(random.nextInt(50));
			nutritional.setVitaminB1(random.nextInt(50));
			nutritional.setVitaminB2(random.nextInt(50));
			nutritional.setVitaminB3(random.nextInt(50));
			nutritional.setVitaminB6(random.nextInt(50));
			nutritional.setVitaminB12(random.nextInt(50));
			nutritional.setVitaminC(random.nextInt(50));
			nutritional.setVitaminD3(random.nextInt(50));
			nutritional.setVitaminE(random.nextInt(50));
			nutritional.setVitaminK(random.nextInt(50));
			nutritional.setZinc(random.nextInt(50));
			nutritional.setSampleTime(1516788245);
			nutritionals.add(nutritional);

		}
		/* 有害物质 */
		for (int i = 0; i < 2; i++) {
			HarmfulSubstances harmfulSubstances1 = new HarmfulSubstances();
			harmfulSubstances1.setArsenic(random.nextInt(50));
			harmfulSubstances1.setElectronicRadiation(random.nextInt(50));
			harmfulSubstances1.setHeavyMetal(random.nextInt(50));
			harmfulSubstances1.setMercury(random.nextInt(50));
			harmfulSubstances1.setPb(random.nextInt(50));
			harmfulSubstances1.setPesticideResidues(random.nextInt(50));
			harmfulSubstances1.setSampleTime(1516788245);
			harmfulSubstances.add(harmfulSubstances1);

		}
		/* 皮肤系统 */
		for (int i = 0; i < 2; i++) {
			Skin skin = new Skin();
			skin.setCollagenIndex(random.nextInt(50));
			skin.setKeratinIndex(random.nextInt(50));
			skin.setMoistureIndex(random.nextInt(50));
			skin.setOilIndex(random.nextInt(50));
			skin.setSampleTime(1516788245);
			skins.add(skin);

		}

		user.setBirthday("1990_12_26");
		user.setHeight(random.nextInt(50));
		user.setWeight(random.nextInt(50));
		user.setSex(1);

		testAdapter = new TestAdapter(this, datas);
		lvTest.setAdapter(testAdapter);
	}

	public void initEvent() {

		lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (position <= 8) {

					if (position == 8) {

						UserApi.setApiCallBack(new ApiHandle() {

							@Override
							public void onSuccess() {
								super.onSuccess();
								ToastUtil.show(MainActivity.this, "数据上传成功");

							}

							@Override
							public void onFailure(int errNo, String errMsg) {
								super.onFailure(errNo, errMsg);

								ToastUtil.show(MainActivity.this, errMsg);

							}
						});
					}
					Intent intent = new Intent(MainActivity.this, activitys[position]);
					startActivity(intent);

				}

				switch (position) {

				case 9:// 视力
					EyesightApi.uploadEyesight(MainActivity.this, "18811427233", eyesights, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 10:// 胆固醇
					CholesterolApi.uploadCholesterol(MainActivity.this, "18811427233", cholesterols, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 11:// 内分泌
					EndocrineApi.uploadEndocrine(MainActivity.this, "18811427233", endocrines, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 12:// 心脑血管
					CardiovascularApi.uploadCardiovascular(MainActivity.this, "18811427233", cardiovasculars,
							new ApiHandle() {

								@Override
								public void onSuccess() {
									super.onSuccess();
									ToastUtil.show(MainActivity.this, "数据上传成功");

								}

								@Override
								public void onFailure(int errNo, String errMsg) {
									super.onFailure(errNo, errMsg);

									ToastUtil.show(MainActivity.this, errMsg);

								}
							});
					break;
				case 13:// 消化系统
					DigestiveApi.uploadDigestive(MainActivity.this, "18811427233", digestives, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 14:// 呼吸系统
					RespiratoryApi.uploadRespiratory(MainActivity.this, "18811427233", respiratories, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 15:// 骨骼系统
					SkeletalApi.uploadSkeletal(MainActivity.this, "18811427233", skeletals, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 16:// 免疫
					ImmuneApi.uploadImmune(MainActivity.this, "18811427233", immunes, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 17:// 男性生殖系统
					MaleReproductiveApi.uploadMaleReproductive(MainActivity.this, "18811427233", maleReproductives,
							new ApiHandle() {

								@Override
								public void onSuccess() {
									super.onSuccess();
									ToastUtil.show(MainActivity.this, "数据上传成功");

								}

								@Override
								public void onFailure(int errNo, String errMsg) {
									super.onFailure(errNo, errMsg);

									ToastUtil.show(MainActivity.this, errMsg);

								}
							});
					break;
				case 18:// 女性生殖系统
					FemaleReproductiveApi.uploadFemaleReproductive(MainActivity.this, "18811427233",
							femaleReproductives, new ApiHandle() {

								@Override
								public void onSuccess() {
									super.onSuccess();
									ToastUtil.show(MainActivity.this, "数据上传成功");

								}

								@Override
								public void onFailure(int errNo, String errMsg) {
									super.onFailure(errNo, errMsg);

									ToastUtil.show(MainActivity.this, errMsg);

								}
							});
					break;
				case 19:// 营养状态
					NutritionalApi.uploadNutritional(MainActivity.this, "18811427233", nutritionals, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 20:// 有害物质
					HarmfulSubstancesApi.uploadHarmfulSubstances(MainActivity.this, "18811427233", harmfulSubstances,
							new ApiHandle() {

								@Override
								public void onSuccess() {
									super.onSuccess();
									ToastUtil.show(MainActivity.this, "数据上传成功");

								}

								@Override
								public void onFailure(int errNo, String errMsg) {
									super.onFailure(errNo, errMsg);

									ToastUtil.show(MainActivity.this, errMsg);

								}
							});
					break;
				case 21:// 皮肤系统
					SkinApi.uploadSkin(MainActivity.this, "18811427233", skins, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				case 22:// 用户
					UserApi.uploadUser(MainActivity.this, "18811427233", user, new ApiHandle() {

						@Override
						public void onSuccess() {
							super.onSuccess();
							ToastUtil.show(MainActivity.this, "数据上传成功");

						}

						@Override
						public void onFailure(int errNo, String errMsg) {
							super.onFailure(errNo, errMsg);

							ToastUtil.show(MainActivity.this, errMsg);

						}
					});
					break;
				}

			}
		});
	}

}

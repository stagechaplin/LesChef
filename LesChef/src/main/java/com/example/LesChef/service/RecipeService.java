package com.example.LesChef.service;

import com.example.LesChef.Repository.RecipeRepository;
import com.example.LesChef.dto.RecipeForm;
import com.example.LesChef.entity.Customer;
import com.example.LesChef.entity.Recipe;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AllCommentService allCommentService;
    private final RecipStepRepository recipStepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public Recipe getrecipe(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        return recipe;
    }

    public List<RecipeForm> geRecipeList(String majorCategory){
        List<Recipe> recipes =recipeRepository.findByMajorCategory(majorCategory);
        return recipes.stream()
                .map(Recipe::toForm)
                .collect(Collectors.toList());
    }

    public List<RecipeIngredientForm> getIngredient(Long Id){
        List<RecipeIngredient> ingredient = recipeIngredientRepository.findByRecipeId(id);
        return ingredient.stream()
                .map(RecipeIngredient::toForm)
                .collect(Collectors.toList());
    }

    public List<RecipeStepForm> getRecipeStep(Long id){
        List<RecipeStep> recipeSteps = recipeStepRepository.findByRecipeId(id);
        return recipeSteps.stream()
                .map(RecipeStep::toForm)
                .collect(Collectors.toList());
    }

    public RecipeForm getRecipeInform(Long id){
        Recipe inform = recipeRepository.findById(id).orElse(null);
        return inform.toForm();
    }

    public Recipe createRecipe(RecipeForm recipeForm, RegistIngredientForm registIngredientForm,
                               RegistStepForm registStepForm, MultipartFile file,
                               List<MultipartFile> stepFile, Customer currentUser){

        List<String> ingredients = registIngredientForm.getIngredients();   //재료이름들
        List<String> quantities = registIngredientForm.getQuantities();     //재료수량들

        List<String> stepWays = registStepForm.getStepWays();               //조리방법들
        log.info("stepFile의 크기: " + stepFile.get(0).getOriginalFilename());


        log.info("레시피등록요청");
        //전달받은 레시피데이터에 작성자의 데이터를 넣기
        recipeForm.setUserId(currentUser);

        try {
            String filePath = "C:/LesChef_note/LesChef/src/main/resources/static/uploads/" + file.getOriginalFilename();
            log.info(filePath);
            log.info("file비어있지않음");
            File dest = new File(filePath);
            //이미지를 파일에 저장
            file.transferTo(dest);
            log.info("여기까지옴2");
            //이미지의 경로 저장
            recipeForm.setRecipeImg("/uploads/" + file.getOriginalFilename());

        } catch (IOException e) {}
        Recipe recipe = recipeForm.toEntity();
        recipeRepository.save(recipe);
        try {
            for(int i = 0; i < stepWays.size(); i++){
                RecipeStep recipeStep = new RecipeStep();
                String stepFilePath = "C:/LesChef_note/LesChef/src/main/resources/static/uploads/" + stepFile.get(i).getOriginalFilename();
                log.info("stepWay의 개수: " + stepWays.size());
                log.info("step의 이미지 경로: " + stepFilePath);
                File stepDest = new File(stepFilePath);
                stepFile.get(i).transferTo(stepDest);
                recipeStep.setRecipe(recipe);
//                recipeStep.setStep_Img(stepImgs.get(i));
                recipeStep.setStepImg("/uploads/" + stepFile.get(i).getOriginalFilename());
                recipeStep.setStepWay(stepWays.get(i));
                recipeStep.setStepNum(i+1L);
                recipeStepRepository.save(recipeStep);
            }
        } catch (IOException e) {}

        log.info("재료이름의 수:" + ingredients.size());
        log.info("재료수량:" + quantities.size());

        // 파일을 지정된 경로에 저장
        for(int i = 0; i < ingredients.size(); i++){
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            log.info("재료 이름:"+ingredients.get(i));
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredientName(ingredients.get(i));
            recipeIngredient.setIngredientVolume(quantities.get(i));
            recipeIngredientRepository.save(recipeIngredient);
        }


        return recipe;
    }

    public List<RecipeForm> getMyRecipeList(String userId){
        List<Recipe> myRecipes = recipeRepository.findMyRecipe(userId);
        return myRecipes.stream()
                .map(Recipe::toForm)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRecipe(Long id){
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        recipeStepRepository.deleteStep(recipe.getRecipeId());
        recipeIngredientRepository.deleteIngredient(recipe.getRecipeId());
        recipeRepository.delete(recipe);
    }

    public void updateRatingAvg(Long recipeId){
        List<Double> ratingAvg = allCommentService.getCommentAvg(recipeId);


        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        log.info("평점은: " + ratingAvg.get(0));
        recipe.setRatingAvg(ratingAvg.get(0));
        recipeRepository.save(recipe);
    }

    @Transactional
    public void increaseViewNum(Long id, HttpServletRequest request, HttpServletResponse response){
        String cookieName = "viewNum_" + id;

        Cookie[] cookies = request.getCookies();
        boolean alreadyViewed = false;

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    alreadyViewed = true;
                    break;
                }
            }
        }

        if(!alreadyViewed){
            recipeRepository.updateRecipeView(id);

            Cookie cookie = new Cookie(cookieName, "true");
            cookie.setMaxAge(30);
            response.addCookie(cookie);
        }
    }

    public void getSortRecipe(String categoryName, String sort, Model model){
        List<Recipe> recipes = recipeRepository.findSortRecipe(categoryName, sort);
        model.addAttribute("recipes", recipes);
    }

    public void recipeEditPage(Long id, Model model){
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        List<RecipeStep> steps = recipeStepRepository.findByRecipeId(id);
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("steps", steps);
        model.addAttribute("ingredients", ingredients);
    }
    //step부분에서 입력칸 생성, 삭제는 되는데 multipartfile에서 이미지를 잘못 받아오는거같음
    @Transactional
    public void editRecipe(Long recipeId, RecipeForm recipeForm, RegistIngredientForm registIngredientForm,
                           RegistStepForm registStepForm, MultipartFile file, List<MultipartFile> stepFile, List<String> firstImage){
        Recipe editRecipe = recipeRepository.findById(recipeId).orElse(null);

        List<RecipeStep> editRecipeStep = recipeStepRepository.findByRecipeId(editRecipe.getRecipeId());
        List<RecipeIngredient> editRecipeIngredient = recipeIngredientRepository.findByRecipeId(editRecipe.getRecipeId());

        editRecipe.setRecipeName(recipeForm.getRecipeName());
        editRecipe.setPortion(recipeForm.getPortion());
        editRecipe.setRunTime(recipeForm.getRunTime());
        editRecipe.setCookLevel(recipeForm.getCookLevel());
        //UPDATE문으로 변경

        List<String> ingredients = registIngredientForm.getIngredients();
        List<String> quantities = registIngredientForm.getQuantities();

        List<String> stepWays = registStepForm.getStepWays();

        Long stepCount = recipeStepRepository.findStepCount(recipeId);

        Long ingredientCount = recipeIngredientRepository.findIngredientCount(recipeId);
        log.info("레시피의 메인 이미지는" + file.getOriginalFilename());
        // 레시피 수정
        try {
            if("".equals(file.getOriginalFilename())) {

                String fileName = editRecipe.getRecipeImg();
                String filePath = "C:/LesChef_note/LesChef/src/main/resources/static" + fileName;
                log.info(filePath);
                log.info("기존 이미지 사용");
//                File dest = new File(filePath);
//                file.transferTo(dest);
//                editRecipe.setRecipeImg(fileName);

            }else{
                String filePath = "C:/LesChef_note/LesChef/src/main/resources/static/uploads/" + file.getOriginalFilename();
                log.info(filePath);
                log.info("새로운 이미지로 변경");
                File dest = new File(filePath);
                file.transferTo(dest);
                editRecipe.setRecipeImg("/uploads/" + file.getOriginalFilename());
            }

        } catch (IOException e) {
            log.info("레시피 오류발생");
        }
        recipeRepository.save(editRecipe).getRecipeId();

        // 조리순서 수정

        try {
            if(stepCount > stepWays.size()){
                for(int i = stepWays.size(); i < stepCount; i++){
                    recipeStepRepository.delete(editRecipeStep.get(i));
                }
            }else if(stepCount < stepWays.size()){
                for(int i = stepCount.intValue(); i < stepWays.size(); i++){
                    RecipeStep newStep = new RecipeStep();
                    newStep.setRecipe(editRecipe);
                    recipeStepRepository.save(newStep);
                }
            }

//            log.info("step의 이미지 이름: " + stepFile.get(2).getOriginalFilename());
            List<RecipeStep> newEditRecipeStep = recipeStepRepository.findByRecipeId(editRecipe.getRecipeId());
            log.info("newEditRecipeStep의 크기는 " + newEditRecipeStep.size());
            for(int i = 0; i < stepWays.size(); i++){
                if(("".equals(stepFile.get(i).getOriginalFilename()))){
                    log.info("조리순서 기존의 이미지업로드"+(i));
                    String stepImg = firstImage.get(i);
                    RecipeStep editStep = newEditRecipeStep.get(i);
                    Long stepId = editStep.getRecipeStepId();
                    Long stepNum = i+1L;
                    String stepWay = stepWays.get(i);
                    recipeStepRepository.updateStep(stepId, recipeId, stepNum, stepWay, stepImg);
                }else {
                    RecipeStep editStep = newEditRecipeStep.get(i);
                    Long stepId = editStep.getRecipeStepId();
                    Long stepNum = i + 1L;
                    String stepWay = stepWays.get(i);


                    //                if("".equals(stepFile.get(i).getOriginalFilename())){
                    //
                    //                }
                    String stepFilePath = "C:/LesChef_note/LesChef/src/main/resources/static/uploads/" + stepFile.get(i).getOriginalFilename();
                    File stepDest = new File(stepFilePath);

                    stepFile.get(i).transferTo(stepDest);

                    String stepImg = "/uploads/" + stepFile.get(i).getOriginalFilename();
                    recipeStepRepository.updateStep(stepId, recipeId, stepNum, stepWay, stepImg);
                    //                recipeStepRepository.save(recipeStep); //Update사용
                }
            }
        } catch (IOException e) {
            log.info("예외발생");
        }


        //재료 수정
        if(ingredientCount > ingredients.size()){
            for(int i = ingredients.size(); i < ingredientCount; i++){
                recipeIngredientRepository.delete(editRecipeIngredient.get(i));
            }
        }else if(ingredientCount < ingredients.size()){
            for(int i = ingredientCount.intValue(); i < ingredients.size(); i++){
                RecipeIngredient newIngredient = new RecipeIngredient();
                newIngredient.setRecipe(editRecipe);
                recipeIngredientRepository.save(newIngredient);
            }
        }
        List<RecipeIngredient> newEditRecipeIngredient = recipeIngredientRepository.findByRecipeId(editRecipe.getRecipeId());
        for(int i = 0; i < ingredients.size(); i++){
            RecipeIngredient editIngredient = newEditRecipeIngredient.get(i);
            Long ingredientId = editIngredient.getRecipeIngredientId();
            String ingredientName = ingredients.get(i);
            String ingredientVolume = quantities.get(i);
            recipeIngredientRepository.updateIngredient(ingredientId, ingredientName, ingredientVolume, recipeId);
        }
    }
}

}

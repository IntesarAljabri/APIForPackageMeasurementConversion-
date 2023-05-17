package com.example.demo.Controllers;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class EquationController  {
        public ResponseEntity<String> solveEquation(String equation){

            if (isValidEquation(equation)) {
                int[] measurements = processEquation(equation);
                return ResponseEntity.ok("The total of measurements is: " + formatMeasurements(measurements));
            } else {
                return ResponseEntity.badRequest().body("Invalid equation format.");
            }
        }

        private boolean isValidEquation (String equation){
            // Check if the equation contains only underscores, letters, and digits
            return equation.matches("[a-za]+");
        }

        private int[] processEquation(String equation){
            String[] segments = equation.split("_");
            int[] segmentValues = new int[segments.length];

            int currentGroup = 0;
            int total = 0;
            boolean skipNext = false;

            for (int i = 0; i < segments.length; i++) {
                String segment = segments[i];

                if (skipNext) {
                    skipNext = false;
                    continue;
                }

                int value;

                if (segment.equals("_")) {
                    value = 0;
                } else if (segment.equals("z")) {
                    if (i + 1 < segments.length && segments[i + 1].equals("z")) {
                        value = 26 + 26;
                        skipNext = true;
                    } else {
                        value = 26;
                    }
                } else {
                    value = segment.charAt(0) - 'a' + 1;
                }

                if (currentGroup % 5 != 0) {
                    segmentValues[currentGroup / 5] += value;
                }

                if (value != 0 && value != 26) {
                    total += value;
                }

                currentGroup++;

                if (currentGroup == 5) {
                    currentGroup = 0;
                }
            }

            return segmentValues;
        }
    private String formatMeasurements(int[] measurements) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < measurements.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            int value = measurements[i];
            if (value > 0) {
                sb.append(value);
            } else {
                sb.append("-");
            }
        }
        return sb.toString();
    }

}

//public class EquationController {
//
//            @PostMapping("solve-equation")
//        public String solveEquation(@RequestParam("equation") String equation, Model model) {
//            int[] result = processEquation(equation);
//            model.addAttribute("result", result);
//            return "result";
//        }
//
//    public int[] processEquation(String equation) {
//            String[] segments = equation.split("_");
//            int[] segmentValues = new int[segments.length];
//
//            int currentGroup = 0;
//            int value;
//            int Total;
//            boolean skipNext = false;
//
//            for (int i = 0; i < segments.length; i++) {
//                String segment = segments[i];
//
//                if (skipNext) {
//                    skipNext = false;
//                    continue;
//                }
//              if ( segments == '_' ) {
//                       segmentValues = new int[]{0};
//                    } else {
//                        segmentValues = segments - 'a' + 1;
//                    }
//
//                    if (!Value) {
//                        Total += segments;
//                        if (segmentValues != 'z') {
//                            currentGroup++;
//                        }
//                        if (segment.equals("z")) {
//                            if (i + 1 < segments.length && segments[i + 1].equals("z")) {
//                                value = 26 + 26;
//                                skipNext = true;
//                            } else {
//                                value = 26;
//                            }
//                        } else {
//                            value = segment.length() > 0 ? segment.charAt(0) - 'a' + 1 : 0;
//                        }
//                    }
//            }
//}

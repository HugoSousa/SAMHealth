<?php 
class Lexical_Process { 
    
    static function get_words($level1, $level2, $level3, $level4, $csv) {
        if (!array_key_exists($level1, $csv)) return null;
        
        $csv_level2 = $csv[$level1];

         if (array_key_exists('elements', $csv_level2))
            return Lexical_Process::dump_words($csv_level2['elements']);
        else {

           if (is_null($level2)) {
                return Lexical_Process::dig_tree($csv_level2);
           } else {
                if (!array_key_exists($level2, $csv_level2)) return null;
                else {
                    $csv_level3 =  $csv_level2[$level2];
                    if (is_null($level3))
                        return Lexical_Process::dig_tree($csv_level3);
                    else {
                        if (!array_key_exists($level3, $csv_level3)) return null;
                        else {
                            $csv_level4 = $csv_level3[$level3];
                            if (is_null($level4))
                              return Lexical_Process::dig_tree($csv_level4);
                          else {
                            if (!array_key_exists($level4, $csv_level4)) return null;
                            else {
                                return Lexical_Process::dig_tree($csv_level4[$level4]);
                            }
                          }
                        }
                    }
                }
           }
        }
        return null;
    } 

    static function dump_words($elements_array) {
        $result = "";
        foreach ($elements_array as $index => $word) {
            $result.= $word." ";
        }
        return rtrim($result, " ");
    }

    static function dig_tree($csv) {
        if (array_key_exists('elements', $csv)) return Lexical_Process::dump_words($csv['elements']);
        else {
            $result = "";

            foreach ($csv as $key => $value) {
                $result .= Lexical_Process::dig_tree($value); 
            }
            return $result;
        }
    }

} 
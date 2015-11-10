<?php 
class CSV { 
    public $path = '?'; 
    
    
    static function get_lexical($path) { 
        //$csv_columns = CSV::get_hardcoded($path); 
        $csv_columns =  CSV::get_csv($path); 

        $lexical = array();
        $stop =false;
        foreach ($csv_columns as &$column) {
        	$array = &$lexical;
        	 $stop =false;
        	foreach ($column as $index => &$element) {
        		        	 if (!$stop) $stop = false;

        		if (strcmp($element, '') != 0 && is_numeric($element)) {
        			//var_dump($element);
        			$stop = true;
        			$array['count'] =  $element;
        			$array['elements'] = array();
        			$array = &$array['elements'];
        		}

        		if (!$stop) {
	        		if (!array_key_exists($element, $array))
	        			$array[$element] =  array();

	        		$array = &$array[$element];
        		} else {
        			if (!is_numeric($element) && strcmp($element, '') != 0) {
        				array_push($array, $element);
        			}
        		}


        	}
        } 

        $clean_lexic = CSV::clean_lexical($lexical);
        return $clean_lexic;
    } 

    static function clean_lexical(&$lexical) {

    	foreach ($lexical as $index => $value) {
    		$res = CSV::recursive_clean($value);
    		if (!is_null($res)) {
    			$lexical[$index] = $res;
    		}
    	}
    	return $lexical;
    }

    static function recursive_clean(&$value) {
    	foreach ($value as $index => $v) {
    		if ($index != '') return null;
    		else{
    			return CSV::dig($value);
    		}  
    	}
    }
	static function dig(&$array) {
		foreach ($array as $key => $value) {
			if (strcmp($key, '') == 0) return CSV::dig($value);
			else return $array;
		}
	}
    static function get_csv($path) {
    	$result = "";
    	$lexical = array();
    	$row = 1;
		if (($handle = fopen($path, "r")) !== FALSE) {
		    while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
		        $num = count($data);
		        $result .= "<p> $num fields in line $row: <br /></p>\n";
		        $row++;
		        for ($c=0; $c < $num; $c++) {
		        	if (!array_key_exists($c, $lexical))
		        		$lexical[$c] = array();
		        	array_push($lexical[$c], $data[$c]);
		            $result .= $data[$c] . "<br />\n";
		        }
		    }
		    fclose($handle);
		}

		return $lexical;
    }
} 
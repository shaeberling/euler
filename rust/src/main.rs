// Copyright 2016 Sascha Haeberling
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

use std::collections::HashSet;
use std::io;
use std::io::prelude::*;
use std::fs::File;

fn read_file() -> Result<String, io::Error> {
    let mut f = try!(File::open("../data/aoc/2016/day1.txt"));
    let mut s = String::new();
    try!(f.read_to_string(&mut s));
    Ok(s)
}

fn process(content: String) {
    let split: Vec<&str> = content.split(", ").collect();
    println!("Item num: {}", split.len());

    let mut pos_x:i32 = 0;
    let mut pos_y:i32= 0;
    let mut dir_x = 0;
    let mut dir_y = 1;
    let mut visited_location = HashSet::new();
    let mut solution_b :i32 = -1;

    for cmd in &split {
        let direction = &cmd[..1];

        if dir_x == 0 {
            dir_x = dir_y * (if direction == "L" { -1 } else { 1 });
            dir_y = 0;
        } else {
            dir_y = dir_x * (if direction == "L" { 1 } else { -1 });
            dir_x = 0;
        }

        let amount = &cmd[1..].parse::<i32>().unwrap();

        let mut x :i32 = 0;
        loop {
            pos_x += dir_x;
            pos_y += dir_y;
            let pos_str = format!("{},{}", pos_x, pos_y);

            if solution_b == -1 && visited_location.contains(&pos_str) {
                solution_b = pos_x.abs() + pos_y.abs();
            }
            visited_location.insert(pos_str);

            x += 1;
            if &x == amount {
                break;
            }
        }
    }
    println!("Solution A: {}, Solution B: {}", pos_x.abs() + pos_y.abs(), solution_b);
}

fn main() {
    println!("AOC Puzzle 1");

    let file_result = read_file();
    match file_result {
        Ok(file) => process(file),
        Err(e) => {
            println!("Ooops. {}", e);
            return ();
        },
    };
}

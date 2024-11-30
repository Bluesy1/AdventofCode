use std::fmt::Display;

type Dir = (i8, i8);
type Point = (i64, i64);
#[allow(unused)]
static ORTHOGONALS: [Dir; 4] = [(0, -1), (1, 0), (0, 1), (-1, 0)];
#[allow(unused)]
static DIAGONALS: [Dir; 4] = [(1, -1), (1, 1), (-1, 1), (-1, -1)];
#[allow(unused)]
static DIRECTIONS: [Dir; 8] = [
    (0, -1),
    (1, 0),
    (0, 1),
    (-1, 0),
    (1, -1),
    (1, 1),
    (-1, 1),
    (-1, -1),
];

#[allow(unused)]
pub fn is_normal(dir: Dir) -> bool {
    vec![dir.0, dir.1].iter().all(|&v| v.abs() <= 1)
}

pub fn is_orthogonal(dir: Dir) -> bool {
    vec![dir.0, dir.1].iter().any(|&v| v == 0)
}

#[allow(unused)]
pub fn is_diagonal(dir: Dir) -> bool {
    !is_orthogonal(dir)
}

#[allow(unused)]
pub fn offset(point: Point, dir: Dir) -> Point {
    (
        point.0 + dir.0 as i64,
        point.1 + dir.1 as i64,
    )
}

#[allow(unused)]
pub fn invert(dir: Dir) -> Dir {
    (-dir.0, -dir.1)
}

pub struct Grid<T> {
    data: Vec<T>,
    width: usize,
    height: usize,
}

#[allow(unused)]
impl<T: Clone + Display> Grid<T> {
    pub fn new(width: usize, height: usize, data: Vec<T>) -> Result<Self, String> {
        if width * height != data.len() {
            return Err(format!(
                "Invalid dimensions: {}x{} != {}",
                width,
                height,
                data.len()
            ));
        }
        Ok(Self {
            data,
            width,
            height,
        })
    }
    pub fn from_default(width: usize, height: usize, default: T) -> Self {
        Self {
            data: vec![default; width * height],
            width,
            height,
        }
    }

    pub fn contains(&self, point: Point) -> bool {
        (point.0 as usize) < self.width && (point.1 as usize)< self.height
    }

    pub fn len(&self) -> usize {
        self.width * self.height
    }

    pub fn get(&self, point: Point) -> Option<&T> {
        if self.contains(point) {
            Some(&self.data[(point.0 + point.1 * (self.width as i64)) as usize])
        } else {
            None
        }
    }

    pub fn set(&mut self, point: Point, value: T) -> Option<T> {
        if self.contains(point) {
            Some(std::mem::replace(
                &mut self.data[(point.0 + point.1 * (self.width as i64)) as usize],
                value,
            ))
        } else {
            None
        }
    }

    pub fn iter(&self) -> impl Iterator<Item = (Point, &T)> {
        self.data
            .iter()
            .enumerate()
            .map(|(i, v)| (((i % self.width) as i64, (i / self.width) as i64), v))
    }

    pub fn count(&self, value: T) -> usize
    where
        T: PartialEq,
    {
        self.data.iter().filter(|v| **v == value).count()
    }

    pub fn count_where<F>(&self, f: F) -> usize
    where
        F: Fn(&T) -> bool,
    {
        self.data.iter().filter(|v| f(*v)).count()
    }

    pub fn take(&self, points: Vec<Point>) -> impl Iterator<Item = &T> {
        points.into_iter().filter_map(move |p| self.get(p))
    }

    pub fn take_where<F>(&self, f: F) -> impl Iterator<Item = &T>
    where
        F: Fn(&T) -> bool,
    {
        self.data.iter().filter(move |v| f(*v))
    }

    pub fn values(&self) -> impl Iterator<Item = &T> {
        self.data.iter()
    }

    pub fn points(&self) -> impl Iterator<Item = Point> + '_ {
        (0..self.width as i64)
            .flat_map(move |x| (0..self.height as i64).map(move |y| (x, y)))
    }

    pub fn points_where<'a, F>(&'a self, f: F) -> impl Iterator<Item = Point> + '_
    where
        F: Fn(&T) -> bool + 'a,
    {
        self.points().filter(move |p| f(self.get(*p).unwrap()))
    }

    pub fn map<G: Clone + Display>(&self, f: impl Fn(&T) -> G) -> Result<Grid<G>, String> {
        Grid::new(self.width, self.height, self.data.iter().map(f).collect())
    }

    pub fn mut_map(&mut self, f: impl Fn(&T) -> T) {
        self.data = self.data.iter().map(f).collect();
    }

    pub fn clone(&self) -> Self {
        Self {
            data: self.data.clone(),
            width: self.width,
            height: self.height,
        }
    }

    pub fn rows(&self) -> impl Iterator<Item = &[T]> {
        self.data.chunks(self.width).into_iter()
    }

    pub fn row_points(&self) -> impl Iterator<Item = Point> {
        (0..self.height).map(move |y| (0, y as i64))
    }

    pub fn row(&self, y: usize) -> &[T] {
        &self.data[y * self.width..(y + 1) * self.width]
    }

    pub fn columns(&self) -> impl Iterator<Item = impl Iterator<Item = &T>> {
        (0..self.width).map(move |x| self.data.iter().skip(x).step_by(self.width))
    }

    pub fn column_points(&self) -> impl Iterator<Item = Point> {
        (0..self.width).map(move |x| (x as i64, 0))
    }

    pub fn column(&self, x: usize) -> impl Iterator<Item = &T> {
        self.data.iter().skip(x).step_by(self.width)
    }

    pub fn h_concat(&self, other: &Self) -> Result<Self, String> {
        let mut data = vec![];
        for (i, (row1, row2)) in self.rows().zip(other.rows()).enumerate() {
            if row1.len() != row2.len() {
                return Err(format!(
                    "Invalid dimensions: {} != {} (on row {})",
                    row1.len(),
                    row2.len(),
                    i
                ));
            }
            data.extend_from_slice(row1);
            data.extend_from_slice(row2);
        }
        Ok(Self {
            data,
            width: self.width + other.width,
            height: self.height,
        })
    }

    pub fn v_concat(&self, other: &Self) -> Result<Self, String> {
        if self.width != other.width {
            return Err(format!(
                "Invalid dimensions: {} != {} (on width)",
                self.width, other.width
            ));
        }
        let mut data = vec![];
        data.extend_from_slice(&self.data);
        data.extend_from_slice(&other.data);
        Ok(Self {
            data,
            width: self.width,
            height: self.height + other.height,
        })
    }

    pub fn flip(self) -> Self {
        let mut data = vec![];
        for row in self.rows() {
            data.extend(row.into_iter().rev().map(|v| v.clone()));
        }
        Self {
            data,
            width: self.width,
            height: self.height,
        }
    }

    pub fn vflip(&self) -> Self {
        let mut data = vec![];
        for i in (0..self.height).rev() {
            data.extend(self.row(i).iter().cloned());
        }
        Self {
            data,
            width: self.width,
            height: self.height,
        }
    }

    pub fn rot(self) -> Self {
        let mut grid = Grid::from_default(self.height, self.width, self.data[0].clone());
        for ((x,y), _) in self.iter() {
            grid.set(((self.width-(y as usize)-1) as i64, x), self.get((x,y)).unwrap().clone());
        }
        grid
    }

    pub fn lrot(&self) -> Self {
        let mut grid = Grid::from_default(self.height, self.width, self.data[0].clone());
        for ((x,y), _) in self.iter() {
            grid.set((y, ((x as usize )-self.height-1) as i64), self.get((x,y)).unwrap().clone());
        }
        grid
    }

    pub fn parse<ConvT, SplitT>(&self, from: &str, convert: ConvT, split: SplitT) -> Result<Grid<T>, String>
    where
        ConvT: Fn(&str) -> Result<T, String>,
        SplitT: Fn(&str) -> Vec<&str>,
    {
        let mut data = vec![];
        for line in from.lines() {
            let mut row = vec![];
            for part in split(line) {
                row.push(convert(part)?);
            }
            data.extend(row);
        }
        Grid::new(self.width, self.height, data)
    }

    pub fn from_vec(vec: Vec<Vec<T>>) -> Result<Self, String> {
        let width = vec[0].len();
        let height = vec.len();
        let mut data = vec![];
        for row in vec {
            if row.len() != width {
                return Err(format!(
                    "Invalid dimensions: {} != {} (on row {})",
                    row.len(),
                    width,
                    data.len() / width
                ));
            }
            data.extend(row);
        }
        Ok(Self {
            data,
            width,
            height,
        })
    }
}

impl<T: Clone + Display> Display for Grid<T> {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        for y in 0..self.height {
            for x in 0..self.width {
                write!(f, "{}", self.get((x as i64, y as i64)).unwrap())?;
            }
            write!(f, "\n")?;
        }
        Ok(())
    }
}
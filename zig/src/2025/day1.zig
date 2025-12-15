const std = @import("std");
const print = std.debug.print;

pub fn read_full(allocator: std.mem.Allocator, path: []const u8) ![]u8 {
    var file = try std.fs.cwd().openFile(path, .{});
    defer file.close();
    const content = try allocator.alloc(u8, (try file.stat()).size);
    _ = try std.fs.Dir.readFile(std.fs.cwd(), path, content);
    return content;
}

fn printDuration(duration: i128) void {
    if (duration < 1_000_000) {
        print("Duration: {d} µs\n", .{@as(f64, @floatFromInt(duration)) / 1_000.0});
    } else if (duration < 1_000_000_000) {
        print("Duration: {d} ms\n", .{@as(f64, @floatFromInt(duration)) / 1_000_000.0});
    } else {
        print("Duration: {d} s\n", .{@as(f64, @floatFromInt(duration)) / 1_000_000_000.0});
    }
}

pub fn part1(input: []u8) !u64 {
    var it = std.mem.splitAny(u8, input, "\n");
    var count: u32 = 0;
    var dial: i32 = 50;
    while (it.next()) |row| {
        if (row.len == 0) break;
        const dir = row[0];
        const rot = try std.fmt.parseInt(i32, row[1..], 10);
        switch (dir) {
            'L' => {
                dial -= rot;
            },
            'R' => {
                dial += rot;
            },
            else => (unreachable),
        }
        dial = @mod(dial, 100);
        if (dial == 0) {
            count += 1;
        }
    }
    return count;
}

pub fn part2(input: []u8) !u64 {
    var it = std.mem.splitAny(u8, input, "\n");
    var count: u32 = 0;
    var dial: i32 = 50;
    while (it.next()) |row| {
        const prev_dial = dial;
        if (row.len == 0) break;
        const dir = row[0];
        const rot = try std.fmt.parseInt(i32, row[1..], 10);
        switch (dir) {
            'L' => {
                dial -= rot;
            },
            'R' => {
                dial += rot;
            },
            else => (unreachable),
        }
        if (prev_dial < dial) {
            count += @abs(@divFloor(prev_dial, 100) - @divFloor(dial, 100));
        } else if (prev_dial > dial) {
            count += @abs(@divFloor(prev_dial - 1, 100) - @divFloor(dial - 1, 100));
        }
    }
    return count;
}

pub fn main() !void {
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    defer _ = gpa.deinit();
    const allocator = gpa.allocator();

    const content = try read_full(allocator, "inputs/2025/day01.txt");
    defer allocator.free(content);

    const start = std.time.nanoTimestamp();

    const ans1 = try part1(content);
    const end = std.time.nanoTimestamp();
    const duration = end - start;

    print("Part 1: {d}\n", .{ans1});
    printDuration(duration);
    const start2 = std.time.nanoTimestamp();
    const ans2 = try part2(content);
    const end2 = std.time.nanoTimestamp();
    const duration2 = end2 - start2;
    print("Part 2: {d}\n", .{ans2});
    printDuration(duration2);
    print("Total Duration: ", .{});
    printDuration(end2 - start);
}

test "day1 part1 example" {
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    defer _ = gpa.deinit();
    const allocator = gpa.allocator();

    const input = try read_full(allocator, "inputs/2025/day01.test.txt");
    defer allocator.free(input);
    const result = try part1(input);
    try std.testing.expectEqual(3, result);
}

test "day1 part1 actual" {
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    defer _ = gpa.deinit();
    const allocator = gpa.allocator();

    const input = try read_full(allocator, "inputs/2025/day01.txt");
    defer allocator.free(input);
    var result = try part1(input);
    try std.testing.expectEqual(1097, result);
    result = try part2(input);
    try std.testing.expectEqual(7101, result);
}

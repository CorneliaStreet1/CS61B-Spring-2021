# Gitlet Design Document

**Name**:Jiangyiqing

- GItlet的功能性：
  - 保存整个Repo的内容，commit
  - 重置一个或几个文件，或者整个commiy。
  - log
  - 维护一系列的commit，也就是一个分支。
  - 分支合并。
  - commit trees are *immutable*: once a commit node has been created, it can never be destroyed (or changed at all). We can only add new things to the commit tree, not modify existing things. This is an important feature of Gitlet! One of Gitlet’s goals is to allow us to save things so we don’t delete them accidentally.
- Objects:
  - ***blobs\***: The saved contents of files. Since Gitlet saves many versions of files, a single file might correspond to multiple blobs: each being tracked in a different commit.
  - ***commits\***: Combinations of log messages, other metadata (commit date, author, etc.), a reference to a tree, and references to parent commits. The repository also maintains a mapping from *branch heads* to references to commits, so that certain important commits have symbolic names.
- 所有的东西都保存在`.gitlet`中
- There are some failure cases you need to handle that don’t apply to a particular command. Here they are:
  - If a user doesn’t input any arguments, print the message `Please enter a command.` and exit.
  - If a user inputs a command that doesn’t exist, print the message `No command with that name exists.` and exit.
  - If a user inputs a command with the wrong number or format of operands, print the message `Incorrect operands.` and exit.
  - If a user inputs a command that requires being in an initialized Gitlet working directory (i.e., one containing a `.gitlet` subdirectory), but is not in such a directory, print the message `Not in an initialized Gitlet directory.`

## Classes and Data Structures

### Class Commit

#### Fields

- ParentCommit：指向前一个Commit的引用。a parent reference, and (for merges) a second parent reference.
- LogMessage
- Timestamp
- a mapping of file names to blob references
- commit id


### Class Repository

#### Fields

**我们不使用真的指针，而是将字符串当做指针来使用，比如指向父母的指针，我们直接记录父母的文件名是什么，这样的。**

**而文件名我们都使用SHA-1Hash。HEAD同样是一个记录当前commit的Hash的字符串**

- HEAD：指向当前所在的commit，正常情况下会指向commit链的最新commit。除非 *detatched head state*
- 指向各个分支的指针。任意时刻只有一个分支的指针活跃。HEAD应该指向当前活跃的分支的最头上（Gitlet不会出现 *detatched head state*）。
- 指向当前活跃的分支的指针。Gitlet只需要支持两个分支即可。
- blobs文件夹，保存所有被追踪的文件的内容的地方。
- commits文件夹，Commits，保存所有Commit的地方
- StageArea文件夹，保存已Add尚未commit的地方

### Class blob

- 

### Class Staging Area

#### Fields

- staged for addition
- staged for removal 

## Algorithms

## Persistence

